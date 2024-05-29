package com.vasscompany.vassuniversitybaseproject.ui.base

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.vasscompany.vassuniversitybaseproject.data.domain.model.error.ErrorModel
import com.vasscompany.vassuniversitybaseproject.ui.dialogfragment.bottomsheet.BaseProjectBottomSheetDialogFragment
import com.vasscompany.vassuniversitybaseproject.ui.extensions.gone
import com.vasscompany.vassuniversitybaseproject.ui.extensions.visible

abstract class BaseFragment<B : ViewBinding> : Fragment() {

    var binding: B? = null

    private lateinit var baseActivity: BaseActivity<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivity = activity as BaseActivity<*>
    }

    override fun onResume() {
        super.onResume()
        configureToolbarAndConfigScreenSections()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        callViewModelSaveData()
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        inflateBinding()
        createViewAfterInflateBinding(inflater, container, savedInstanceState)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        observeViewModel()
        viewCreatedAfterSetupObserverViewModel(view, savedInstanceState)
    }

    fun hideToolbar() {
        baseActivity.hideToolbar()
    }

    fun showToolbar(
        showBack: Boolean = false,
        showMenu: Boolean = false,
        title: String = "",
        showNotification: Boolean = false,
        showClose: Boolean = false,
    ) {
        baseActivity.showToolbar(
            showBack = showBack,
            showMenu = showMenu,
            title = title,
            showNotification = showNotification,
            showClose = showClose
        )
    }

    fun updateShowToolbarBack(showBack: Boolean) {
        baseActivity.updateShowToolbarBack(showBack)
    }

    fun updateShowToolbarMenu(showMenu: Boolean) {
        baseActivity.updateShowToolbarMenu(showMenu)
    }

    fun updateShowToolbarNotification(showNotification: Boolean) {
        baseActivity.updateShowToolbarNotification(showNotification)
    }

    fun updateShowToolbarTitle(title: String) {
        baseActivity.updateShowToolbarTitle(title)
    }

    fun fragmentFullScreenLayoutWithoutToolbar() {
        baseActivity.fragmentFullScreenLayoutWithoutToolbar()
    }

    fun fragmentLayoutWithToolbar() {
        baseActivity.fragmentLayoutWithToolbar()
    }

    fun checkErrorNeedFinishSession401(errorModel: ErrorModel): Boolean {
        return baseActivity.checkErrorNeedFinishSession401(errorModel)
    }

    fun showLoading(show: Boolean) {
        baseActivity.showLoading(show)
    }

    fun closeSessionTokenExpired() {
        baseActivity.goToLoginSession401()
    }

    fun showBottomSheetDialogFragment(
        baseProjectBottomSheetDialogFragmentListener: BaseProjectBottomSheetDialogFragment.BaseProjectBottomSheetDialogFragmentListener,
        title: String,
        message: String,
        drawableIcon: Drawable? = null,
        textButtonPositive: String? = null,
        textButtonNegative: String? = null,
        showClose: Boolean
    ) {
        baseActivity.showBottomSheetDialogFragment(
            baseProjectBottomSheetDialogFragmentListener,
            title,
            message,
            drawableIcon,
            textButtonPositive,
            textButtonNegative,
            showClose
        )
    }

    fun showBottomSheetDialogFragment(
        baseProjectBottomSheetDialogFragmentListener: BaseProjectBottomSheetDialogFragment.BaseProjectBottomSheetDialogFragmentListener,
        errorModel: ErrorModel,
        drawableIcon: Drawable? = null,
        textButtonPositive: String? = null,
        textButtonNegative: String? = null,
        showClose: Boolean
    ) {
        baseActivity.showBottomSheetDialogFragment(
            baseProjectBottomSheetDialogFragmentListener,
            errorModel,
            drawableIcon,
            textButtonPositive,
            textButtonNegative,
            showClose
        )
    }

    fun showBottomSheetDialogFragment(errorModel: ErrorModel) {
        baseActivity.showBottomSheetDialogFragment(errorModel)
    }

    fun showNoResults(gResults: Group?, tvNoResult: TextView?) {
        gResults?.gone()
        tvNoResult?.visible()
    }

    fun hideNoResults(gResults: Group?, tvNoResult: TextView?) {
        gResults?.visible()
        tvNoResult?.gone()
    }

    fun showDialogFragment(dialogFragment: DialogFragment, tag: String) {
        baseActivity.showDialogFragment(dialogFragment, tag)
    }

    fun removeFragmentByTag(tag: String) {
        baseActivity.removeFragmentByTag(tag)
    }

    fun hideSwipeDrawerMenu() {
        baseActivity.hideSwipeDrawerMenu()
    }

    fun hideKeyboard() {
        baseActivity.hideKeyboard()
    }

    open fun clickMyFiatToolbar() = Unit

    open fun clickMyCryptoToolbar() = Unit

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    protected open fun setupViewModel() = Unit

    abstract fun inflateBinding()
    abstract fun createViewAfterInflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )

    open fun callViewModelSaveData() = Unit
    abstract fun observeViewModel()
    abstract fun viewCreatedAfterSetupObserverViewModel(view: View, savedInstanceState: Bundle?)
    abstract fun configureToolbarAndConfigScreenSections()
}