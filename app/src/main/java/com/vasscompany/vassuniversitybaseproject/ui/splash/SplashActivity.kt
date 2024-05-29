package com.vasscompany.vassuniversitybaseproject.ui.splash

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.vasscompany.vassuniversitybaseproject.R
import com.vasscompany.vassuniversitybaseproject.ui.dialogfragment.bottomsheet.BaseProjectBottomSheetDialogFragment
import com.vasscompany.vassuniversitybaseproject.ui.main.MainActivity
import com.vasscompany.vassuniversitybaseproject.utils.DataDeviceUtils.Companion.isDeviceRoot
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()
    private lateinit var screenSplash: androidx.core.splashscreen.SplashScreen
    private var baseProjectBottomSheetDialogFragment: BaseProjectBottomSheetDialogFragment = BaseProjectBottomSheetDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screenSplash = installSplashScreen()
        observeViewModel()
        configureSplash()
        splashViewModel.restoreDataViewModelIfExists(splashViewModel.getKeysNeedSaveStateHandler())
        splashViewModel.checkVersion()
    }

    private fun configureSplash() {
        screenSplash.apply {
            setKeepOnScreenCondition {
                return@setKeepOnScreenCondition splashViewModel.needWait()
            }
            setOnExitAnimationListener {
                if (!splashViewModel.needShowAlertMessage()) {
                    if (!isDeviceRoot(this@SplashActivity)) {
                        finishSplash()
                    } else {
                        showBottomSheetDialogFragment(
                            baseProjectBottomSheetDialogFragmentListener = object :
                                BaseProjectBottomSheetDialogFragment.BaseProjectBottomSheetDialogFragmentListener {
                                override fun onBaseProjectBottomSheetDialogFragmentClickClose() = Unit

                                override fun onBaseProjectBottomSheetDialogFragmentClickButtonPositive() {
                                    finish()
                                }

                                override fun onBaseProjectBottomSheetDialogFragmentClickButtonNegative() = Unit
                            },
                            title = getString(R.string.general_root_title),
                            message = getString(R.string.general_root_description),
                            drawableIcon = ContextCompat.getDrawable(this@SplashActivity, R.drawable.ic_error),
                            textButtonPositive = getString(R.string.general_accept),
                            textButtonNegative = null,
                            showClose = false
                        )
                    }
                }
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            splashViewModel.errorFlow.collect {
                showBottomSheetDialogFragment(
                    baseProjectBottomSheetDialogFragmentListener = object :
                        BaseProjectBottomSheetDialogFragment.BaseProjectBottomSheetDialogFragmentListener {
                        override fun onBaseProjectBottomSheetDialogFragmentClickClose() = Unit

                        override fun onBaseProjectBottomSheetDialogFragmentClickButtonPositive() {
                            finish()
                        }

                        override fun onBaseProjectBottomSheetDialogFragmentClickButtonNegative() = Unit
                    },
                    title = getString(R.string.popup_default_title),
                    message = it.message,
                    drawableIcon = null,
                    textButtonPositive = getString(R.string.general_accept),
                    textButtonNegative = null,
                    showClose = false
                )
            }
        }
    }

    private fun showBottomSheetDialogFragment(
        baseProjectBottomSheetDialogFragmentListener: BaseProjectBottomSheetDialogFragment.BaseProjectBottomSheetDialogFragmentListener,
        title: String = "",
        message: String = "",
        drawableIcon: Drawable? = null,
        textButtonPositive: String? = null,
        textButtonNegative: String? = null,
        showClose: Boolean
    ) {
        if (supportFragmentManager.findFragmentByTag(BaseProjectBottomSheetDialogFragment.BASE_PROJECT_BOTTOM_SHEET_DIALOG_FRAGMENT_TAG) == null) {
            baseProjectBottomSheetDialogFragment = BaseProjectBottomSheetDialogFragment()
            baseProjectBottomSheetDialogFragment.setValue(
                baseProjectBottomSheetDialogFragmentListener,
                title,
                message,
                drawableIcon,
                textButtonPositive,
                textButtonNegative,
                showClose
            )
            baseProjectBottomSheetDialogFragment.show(
                supportFragmentManager,
                BaseProjectBottomSheetDialogFragment.BASE_PROJECT_BOTTOM_SHEET_DIALOG_FRAGMENT_TAG
            )
        } else {
            baseProjectBottomSheetDialogFragment.refreshValue(
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

    private fun finishSplash() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}