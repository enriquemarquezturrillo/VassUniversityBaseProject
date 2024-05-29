package com.vasscompany.vassuniversitybaseproject.ui.base

import android.content.Intent
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updateLayoutParams
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding
import com.vasscompany.vassuniversitybaseproject.R
import com.vasscompany.vassuniversitybaseproject.data.constants.GeneralConstants.Companion.GENERAL_ERROR_401_UNAUTHORIZED
import com.vasscompany.vassuniversitybaseproject.data.constants.GeneralConstants.Companion.URL_PDF_BASE
import com.vasscompany.vassuniversitybaseproject.data.domain.model.error.ErrorModel
import com.vasscompany.vassuniversitybaseproject.ui.customview.FullDrawerLayout
import com.vasscompany.vassuniversitybaseproject.ui.dialogfragment.bottomsheet.BaseProjectBottomSheetDialogFragment
import com.vasscompany.vassuniversitybaseproject.ui.dialogfragment.bottomsheet.BaseProjectBottomSheetDialogFragment.Companion.BASE_PROJECT_BOTTOM_SHEET_DIALOG_FRAGMENT_TAG
import com.vasscompany.vassuniversitybaseproject.ui.dialogfragment.loading.LoadingDialogFragment
import com.vasscompany.vassuniversitybaseproject.ui.dialogfragment.loading.LoadingDialogFragment.Companion.LOADING_DIALOG_FRAGMENT_TAG
import com.vasscompany.vassuniversitybaseproject.ui.extensions.gone
import com.vasscompany.vassuniversitybaseproject.ui.extensions.visible
import com.vasscompany.vassuniversitybaseproject.ui.main.MainActivity
import com.vasscompany.vassuniversitybaseproject.utils.WebUtils
import javax.inject.Inject

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity(), View.OnClickListener {

    enum class BottomMenuTab {
        FIAT_TAB_MY_ACCOUNTS, FIAT_TAB_MY_TRANSACTIONS, CRYPTO_TAB_MY_POSITION, CRYPTO_TAB_MY_ACTIVITY
    }

    lateinit var binding: B

    private var isKeyboardVisible = false
    private var clToolbar: ConstraintLayout? = null
    private var tbToolbar: Toolbar? = null
    private var ibToolbarBack: ImageButton? = null
    private var ibToolbarMenu: ImageButton? = null
    private var tvToolbarTitle: TextView? = null
    private var ibToolbarClose: ImageButton? = null
    private var ibToolbarNotification: ImageButton? = null
    private var tvToolbarNotification: TextView? = null
    private var ivClose: ImageView? = null
    private var dlDashboard: FullDrawerLayout? = null

    private var loadingDialogFragment: LoadingDialogFragment = LoadingDialogFragment()
    private var baseprojectBottomSheetDialogFragment: BaseProjectBottomSheetDialogFragment = BaseProjectBottomSheetDialogFragment()

    private var detectFail401FinishSessionGoToLoginStartProcess = false

    @Inject
    lateinit var baseActivityControlShowLoading: BaseActivityControlShowLoading

    lateinit var navController: NavController
    var countNotifications: Int = 0

    override fun onResume() {
        super.onResume()
        configureToolbarAndConfigScreenSections()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        callViewModelSaveData()
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflateBinding()
        setContentView(binding.root)
        findViewByIdToolbar()
        setListenerKeyboardIsVisible()
        setupViewModel()
        observeViewModel()
        createAfterInflateBindingSetupObserverViewModel(savedInstanceState)
        setListenersClickToolbarButtons()
        fixProblemAddFragmentNotCallConfigureToolbar()
    }

    private fun fixProblemAddFragmentNotCallConfigureToolbar() {
        supportFragmentManager.addOnBackStackChangedListener {
            val numbersFragments = supportFragmentManager.fragments.size
            if (numbersFragments > 0) {
                var calledConfigureToolbar = false
                var nextFragment = numbersFragments - 1

                while (!calledConfigureToolbar && nextFragment >= 0) {
                    if (supportFragmentManager.fragments[nextFragment] is BaseFragment<*>) {
                        (supportFragmentManager.fragments[nextFragment] as BaseFragment<*>).configureToolbarAndConfigScreenSections()
                        calledConfigureToolbar = true
                    }
                    nextFragment--
                }
            }
        }
    }

    private fun findViewByIdToolbar() {
        clToolbar = findViewById(R.id.clToolbar)
        tbToolbar = findViewById(R.id.tbToolbar)
        ibToolbarBack = findViewById(R.id.ibToolbarBack)
        ibToolbarMenu = findViewById(R.id.ibToolbarMenu)
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle)
        ibToolbarClose = findViewById(R.id.ibToolbarClose)
        ibToolbarNotification = findViewById(R.id.ibToolbarNotification)
        tvToolbarNotification = findViewById(R.id.tvToolbarNotification)
        ivClose = findViewById(R.id.ivClose)
        dlDashboard = findViewById(R.id.dlDashboard)
    }

    private fun setListenerKeyboardIsVisible() {
        val rootViewLayout = window.decorView.rootView
        rootViewLayout.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            rootViewLayout.getWindowVisibleDisplayFrame(r)
            val screenHeight = rootViewLayout.rootView.height
            // r.bottom is the position above soft keypad or device button.
            // if keypad is shown, the r.bottom is smaller than that before.
            val keypadHeight = screenHeight - r.bottom
            //Log.d(TAG, "l> keypadHeight = $keypadHeight")
            if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                // keyboard is opened
                if (!isKeyboardVisible) {
                    isKeyboardVisible = true
                }
            } else {
                // keyboard is closed
                if (isKeyboardVisible) {
                    isKeyboardVisible = false
                }
            }
        }
    }

    private fun setListenersClickToolbarButtons() {
        ibToolbarBack?.setOnClickListener(this)
        ibToolbarMenu?.setOnClickListener(this)
        ibToolbarClose?.setOnClickListener(this)
        ibToolbarNotification?.setOnClickListener(this)
        ivClose?.setOnClickListener((this))
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ibToolbarBack -> clickToolbarBack()
            R.id.ibToolbarMenu -> clickToolbarMenu()
            R.id.ibToolbarNotification -> clickToolbarNotification()
            R.id.ibToolbarClose -> clickToolbarClose()
            R.id.ivClose -> clickToolbarMenu()
        }
    }

    protected open fun clickToolbarBack() {
        onBackPressed()
    }

    protected open fun clickToolbarMenu() = Unit

    protected open fun clickToolbarNotification() {
        goToNotificationInboxActivity()
    }

    protected open fun goToNotificationInboxActivity() = Unit
    protected open fun clickToolbarNotificationDelete() = Unit
    protected open fun clickToolbarNotificationMarkReaded() = Unit
    protected open fun clickToolbarClose() = Unit

    fun popAllPreviousFragments() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            for (i in 0 until supportFragmentManager.backStackEntryCount) {
                supportFragmentManager.popBackStack()
            }
        }
    }

    fun goBackNumberSteps(steps: Int) {
        var counter = steps
        if (supportFragmentManager.fragments.size > 0 && supportFragmentManager.fragments.size >= steps) {
            while (counter > 0) {
                counter--
                onBackPressed()
            }
        }
    }

    fun existsFragmentWithTAG(tag: String): Boolean {
        return supportFragmentManager.findFragmentByTag(tag) != null
    }

    fun removeFragmentByTag(tag: String) {
        val findFragmentByTag = supportFragmentManager.findFragmentByTag(tag)
        if (findFragmentByTag != null) {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.remove(findFragmentByTag)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            transaction.commit()
        }
    }

    fun isKeyboardVisible(): Boolean {
        return isKeyboardVisible
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, 0)
        }
    }

    fun hideToolbar() {
        tbToolbar?.gone()
    }

    private fun hideAllElementToolbar() {
        ibToolbarBack?.gone()
        ibToolbarMenu?.gone()
        tvToolbarTitle?.gone()
        ibToolbarClose?.gone()
        ibToolbarNotification?.gone()
        tvToolbarNotification?.gone()
    }

    fun showToolbar(
        showBack: Boolean = false,
        showMenu: Boolean = false,
        title: String = "",
        showNotification: Boolean = false,
        showClose: Boolean = false,
    ) {
        var maxIconLeft = 0
        var maxIconRight = 0

        hideAllElementToolbar()
        tbToolbar?.visible()
        if (showBack) {
            maxIconLeft++
            ibToolbarBack?.visible()
        }
        if (showMenu) {
            maxIconLeft++
            ibToolbarMenu?.visible()
        }
        if (showNotification) {
            maxIconRight++
            ibToolbarNotification?.visible()
            checkShowToolbarNotificationIconHaveMessageUnread()
        }
        if (showClose) {
            maxIconRight++
            ibToolbarClose?.visible()
        }
        if (title.isNotBlank()) {
            tvToolbarTitle?.visible()
            tvToolbarTitle?.text = title
            if (maxIconLeft > maxIconRight) {
                configMarginTitle(maxIconLeft)
            } else {
                configMarginTitle(maxIconRight)
            }
        }
    }

    private fun configMarginTitle(numberIconsLeftRightTitle: Int) {
        if (tvToolbarTitle != null) {
            tvToolbarTitle!!.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                when (numberIconsLeftRightTitle) {
                    0 -> {
                        marginStart =
                            resources.getDimension(R.dimen.toolbar_margin_title_zero_buttons)
                                .toInt()
                        marginEnd =
                            resources.getDimension(R.dimen.toolbar_margin_title_zero_buttons)
                                .toInt()
                    }

                    1 -> {
                        marginStart =
                            resources.getDimension(R.dimen.toolbar_margin_title_one_buttons).toInt()
                        marginEnd =
                            resources.getDimension(R.dimen.toolbar_margin_title_one_buttons).toInt()
                    }

                    2 -> {
                        marginStart =
                            resources.getDimension(R.dimen.toolbar_margin_title_two_buttons).toInt()
                        marginEnd =
                            resources.getDimension(R.dimen.toolbar_margin_title_two_buttons).toInt()
                    }

                    3 -> {
                        marginStart =
                            resources.getDimension(R.dimen.toolbar_margin_title_three_buttons)
                                .toInt()
                        marginEnd =
                            resources.getDimension(R.dimen.toolbar_margin_title_three_buttons)
                                .toInt()
                    }

                    else -> {
                        marginStart =
                            resources.getDimension(R.dimen.toolbar_margin_title_four_buttons)
                                .toInt()
                        marginEnd =
                            resources.getDimension(R.dimen.toolbar_margin_title_four_buttons)
                                .toInt()
                    }
                }
            }
        }
    }

    fun updateShowToolbarBack(showBack: Boolean) {
        if (showBack) {
            ibToolbarBack?.visible()
        } else {
            ibToolbarBack?.gone()
        }
    }

    fun updateShowToolbarMenu(showMenu: Boolean) {
        if (showMenu) {
            ibToolbarMenu?.visible()
        } else {
            ibToolbarMenu?.gone()
        }
    }

    fun updateShowToolbarNotification(showNotification: Boolean) {
        if (showNotification) {
            ibToolbarNotification?.visible()
        } else {
            ibToolbarNotification?.gone()
        }
    }

    private fun hideToolbarLayout() {
        clToolbar?.gone()
    }

    private fun showToolbarLayout() {
        clToolbar?.visible()
    }

    fun fragmentFullScreenLayoutWithoutToolbar() {
        hideToolbarLayout()
    }

    fun fragmentLayoutWithToolbar() {
        showToolbarLayout()
    }

    private fun checkShowToolbarNotificationIconHaveMessageUnread() {
        if (ibToolbarNotification?.visibility == View.VISIBLE) {
            if (countNotifications == 0) {
                tvToolbarNotification?.gone()
            } else {
                tvToolbarNotification?.visible()
                tvToolbarNotification?.text = countNotifications.toString()
            }
        }
    }

    fun updateShowToolbarTitle(title: String) {
        if (title.isNotBlank()) {
            tvToolbarTitle?.visible()
            tvToolbarTitle?.text = title
        } else {
            tvToolbarTitle?.gone()
        }
    }

    fun getToolbarTitleLowerCase(): String {
        return if (tvToolbarTitle != null && tvToolbarTitle?.text != null) {
            tvToolbarTitle?.text.toString().lowercase()
        } else {
            ""
        }
    }

    fun showLoading(show: Boolean) {
        if (show) {
            if (baseActivityControlShowLoading.canShowLoading(supportFragmentManager, LOADING_DIALOG_FRAGMENT_TAG)) {
                loadingDialogFragment.show(supportFragmentManager, LOADING_DIALOG_FRAGMENT_TAG)
            }
        } else {
            if (baseActivityControlShowLoading.canHideLoading(supportFragmentManager, LOADING_DIALOG_FRAGMENT_TAG)) {
                loadingDialogFragment.dismiss()
            }
        }
    }

    fun showBottomSheetDialogFragment(
        baseProjectBottomSheetDialogFragmentListener: BaseProjectBottomSheetDialogFragment.BaseProjectBottomSheetDialogFragmentListener,
        title: String = "",
        message: String = "",
        drawableIcon: Drawable? = null,
        textButtonPositive: String? = null,
        textButtonNegative: String? = null,
        showClose: Boolean
    ) {
        if (supportFragmentManager.findFragmentByTag(BASE_PROJECT_BOTTOM_SHEET_DIALOG_FRAGMENT_TAG) == null) {
            baseprojectBottomSheetDialogFragment = BaseProjectBottomSheetDialogFragment()
            baseprojectBottomSheetDialogFragment.setValue(
                baseProjectBottomSheetDialogFragmentListener,
                title,
                message,
                drawableIcon,
                textButtonPositive,
                textButtonNegative,
                showClose
            )
            baseprojectBottomSheetDialogFragment.show(
                supportFragmentManager,
                BASE_PROJECT_BOTTOM_SHEET_DIALOG_FRAGMENT_TAG
            )
        } else {
            baseprojectBottomSheetDialogFragment.refreshValue(
                baseProjectBottomSheetDialogFragmentListener,
                title,
                message,
                drawableIcon,
                textButtonPositive,
                textButtonNegative,
                showClose
            )
        }
    }

    fun showBottomSheetDialogFragment(
        baseProjectBottomSheetDialogFragmentListener: BaseProjectBottomSheetDialogFragment.BaseProjectBottomSheetDialogFragmentListener,
        errorModel: ErrorModel,
        drawableIcon: Drawable? = null,
        textButtonPositive: String? = null,
        textButtonNegative: String? = null,
        showClose: Boolean
    ) {
        if (supportFragmentManager.findFragmentByTag(BASE_PROJECT_BOTTOM_SHEET_DIALOG_FRAGMENT_TAG) == null) {
            baseprojectBottomSheetDialogFragment = BaseProjectBottomSheetDialogFragment()
            baseprojectBottomSheetDialogFragment.setValue(
                baseProjectBottomSheetDialogFragmentListener,
                errorModel,
                drawableIcon,
                textButtonPositive,
                textButtonNegative,
                showClose
            )
            baseprojectBottomSheetDialogFragment.show(
                supportFragmentManager,
                BASE_PROJECT_BOTTOM_SHEET_DIALOG_FRAGMENT_TAG
            )
        } else {
            baseprojectBottomSheetDialogFragment.refreshValue(
                baseProjectBottomSheetDialogFragmentListener,
                errorModel,
                drawableIcon,
                textButtonPositive,
                textButtonNegative,
                showClose
            )
        }
    }

    fun showBottomSheetDialogFragment(errorModel: ErrorModel) {
        if (supportFragmentManager.findFragmentByTag(BASE_PROJECT_BOTTOM_SHEET_DIALOG_FRAGMENT_TAG) == null) {
            baseprojectBottomSheetDialogFragment = BaseProjectBottomSheetDialogFragment()
            baseprojectBottomSheetDialogFragment.setValue(
                object : BaseProjectBottomSheetDialogFragment.BaseProjectBottomSheetDialogFragmentListener {
                    override fun onBaseProjectBottomSheetDialogFragmentClickClose() = Unit
                    override fun onBaseProjectBottomSheetDialogFragmentClickButtonPositive() = Unit
                    override fun onBaseProjectBottomSheetDialogFragmentClickButtonNegative() = Unit
                },
                errorModel = errorModel,
                drawableIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_error, null),
                textButtonPositive = getString(R.string.general_accept),
                textButtonNegative = null,
                showClose = false
            )
            baseprojectBottomSheetDialogFragment.show(
                supportFragmentManager,
                BASE_PROJECT_BOTTOM_SHEET_DIALOG_FRAGMENT_TAG
            )
        } else {
            baseprojectBottomSheetDialogFragment.refreshValue(
                object : BaseProjectBottomSheetDialogFragment.BaseProjectBottomSheetDialogFragmentListener {
                    override fun onBaseProjectBottomSheetDialogFragmentClickClose() = Unit
                    override fun onBaseProjectBottomSheetDialogFragmentClickButtonPositive() = Unit
                    override fun onBaseProjectBottomSheetDialogFragmentClickButtonNegative() = Unit
                },
                errorModel = errorModel,
                drawableIcon = ResourcesCompat.getDrawable(resources, R.drawable.warning, null),
                textButtonPositive = "Aceptar",
                textButtonNegative = null,
                showClose = false
            )
        }
    }

    fun checkErrorNeedFinishSession401(errorModel: ErrorModel): Boolean {
        if (!detectFail401FinishSessionGoToLoginStartProcess && (errorModel.errorCode == GENERAL_ERROR_401_UNAUTHORIZED.toString())) {
            detectFail401FinishSessionGoToLoginStartProcess = true
        }
        return detectFail401FinishSessionGoToLoginStartProcess
    }

    fun showDialogFragment(dialogFragment: DialogFragment, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            dialogFragment.show(supportFragmentManager, tag)
        }
    }

    fun goToLoginSession401() {
        detectFail401FinishSessionGoToLoginStartProcess = true
        val bottomSheetDialogFragment = BaseProjectBottomSheetDialogFragment()
        bottomSheetDialogFragment.setValue(
            baseProjectBottomSheetDialogFragmentListener = object :
                BaseProjectBottomSheetDialogFragment.BaseProjectBottomSheetDialogFragmentListener {
                override fun onBaseProjectBottomSheetDialogFragmentClickClose() = Unit
                override fun onBaseProjectBottomSheetDialogFragmentClickButtonPositive() {
                    finishSessionUserLogout()
                }

                override fun onBaseProjectBottomSheetDialogFragmentClickButtonNegative() = Unit
            },
            title = getString(R.string.general_sesion_expired),
            message = "",
            drawableIcon = ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_error,
                null
            ),
            textButtonPositive = getString(R.string.general_accept),
            textButtonNegative = null,
            showClose = false
        )
        bottomSheetDialogFragment.show(
            supportFragmentManager,
            BASE_PROJECT_BOTTOM_SHEET_DIALOG_FRAGMENT_TAG
        )
    }

    fun finishSessionUserLogout() {
        baseContext.startActivity(
            Intent.makeRestartActivityTask(
                Intent(baseContext, MainActivity::class.java).component
            )
        )
    }

    fun openUrlInsideAppAddUrlPdfBaseAutomatically(url: String) {
        openUrlInsideAppNotAddBaseUrl("$URL_PDF_BASE$url")
    }

    fun openUrlInsideAppNotAddBaseUrl(url: String) {
        WebUtils.openUrlInsideApp(this, url)
    }

    fun isAnyDialogFragmentVisible(): Boolean {
        val fragmentManager = supportFragmentManager

        for (fragment in fragmentManager.fragments) {
            if (fragment is DialogFragment && fragment.isResumed) {
                return true
            }
        }

        return false
    }

    fun ifAnyDialogFragmentVisibleDismissAll() {
        val fragmentManager = supportFragmentManager
        for (fragment in fragmentManager.fragments) {
            if (fragment is DialogFragment && fragment.isResumed) {
                fragment.dismiss()
            }
        }
    }

    fun hideSwipeDrawerMenu() {
        dlDashboard?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    protected open fun activityOnSocketConnectFinishRetries() = Unit

    protected open fun activityOnSocketMessage(text: String?) = Unit

    protected open fun setupViewModel() = Unit

    protected open fun callViewModelSaveData() = Unit

    protected open fun clickMyFiatToolbar() = Unit

    protected open fun clickMyCryptoToolbar() = Unit

    protected open fun clickOnTabSectionBottomMenu(sectionBottomMenuTabClicked: BottomMenuTab) = Unit

    abstract fun inflateBinding()
    abstract fun observeViewModel()
    abstract fun createAfterInflateBindingSetupObserverViewModel(savedInstanceState: Bundle?)
    abstract fun configureToolbarAndConfigScreenSections()
}