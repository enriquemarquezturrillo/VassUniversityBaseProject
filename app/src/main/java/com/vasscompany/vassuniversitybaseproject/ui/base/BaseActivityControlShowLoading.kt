package com.vasscompany.vassuniversitybaseproject.ui.base

import androidx.fragment.app.FragmentManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseActivityControlShowLoading @Inject constructor() {

    private var loadingIsShowing: Boolean = false

    @Synchronized
    fun canShowLoading(fragmentManager: FragmentManager, tag: String): Boolean {
        val canShow = if (loadingIsShowing) {
            false
        } else {
            fragmentManager.findFragmentByTag(tag) == null
        }
        if (canShow) {
            loadingIsShowing = true
        }
        return canShow
    }

    @Synchronized
    fun canHideLoading(fragmentManager: FragmentManager, tag: String): Boolean {
        val canHide = if (loadingIsShowing) {
            true
        } else {
            fragmentManager.findFragmentByTag(tag) != null
        }
        if (canHide) {
            loadingIsShowing = false
        }
        return canHide
    }

}