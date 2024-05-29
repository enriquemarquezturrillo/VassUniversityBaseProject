package com.vasscompany.vassuniversitybaseproject.ui.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.vasscompany.vassuniversitybaseproject.R
import com.vasscompany.vassuniversitybaseproject.databinding.ActivityMainBinding
import com.vasscompany.vassuniversitybaseproject.ui.base.BaseActivity
import com.vasscompany.vassuniversitybaseproject.ui.extensions.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun inflateBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun observeViewModel() {
        lifecycleScope.launch { mainViewModel.loadingFlow.collect { showLoading(it) } }
        lifecycleScope.launch {
            mainViewModel.errorFlow.collect {
                if (checkErrorNeedFinishSession401(errorModel = it)) {
                    goToLoginSession401()
                } else {
                    showBottomSheetDialogFragment(it)
                }
            }
        }

        lifecycleScope.launch {
            mainViewModel.unreadNotify.collect {
                countNotifications = it
            }
        }
    }

    override fun createAfterInflateBindingSetupObserverViewModel(savedInstanceState: Bundle?) {
        configNavigation()
    }

    private fun configNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun configureToolbarAndConfigScreenSections() = Unit

    override fun clickToolbarMenu() {
        super.clickToolbarMenu()
        if (binding.dlDashboard.isDrawerOpen(GravityCompat.START)) {
            binding.dlDashboard.closeDrawer(GravityCompat.START)
        } else {
            binding.dlDashboard.openDrawer(GravityCompat.START)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "l> onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "l> onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "l>> onResume")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "l> onCreateOptionsMenu")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "l> onPrepareOptionsMenu")
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "l>> onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "l> onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "l> onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "l> onDestroy")
    }
}