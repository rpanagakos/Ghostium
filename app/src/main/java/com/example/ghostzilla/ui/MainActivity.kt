package com.example.ghostzilla.ui

import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractActivity
import com.example.ghostzilla.abstraction.AbstractViewModel
import com.example.ghostzilla.ui.tabs.home.HomeFragment
import com.example.ghostzilla.ui.tabs.profile.ProfileFragment
import com.example.ghostzilla.ui.tabs.trends.TrendsFragment
import com.example.ghostzilla.ui.tabs.trends.TrendsViewModel
import com.example.ghostzilla.ui.tabs.wallet.WalletFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AbstractActivity(R.layout.activity_main) {

    private lateinit var navController: NavController

    override fun initLayout() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)
        bottomNavigation.setupWithNavController(navController)
        observeViewModel()
    }

    private fun observeViewModel() {
    }

    override fun runOperation() {
    }

    override fun stopOperation() {
    }

    override fun onBackPressed() {
        when (nav_host_fragment_container.childFragmentManager.fragments[0]) {
            is HomeFragment, is TrendsFragment,
            is WalletFragment, is ProfileFragment -> finishAffinity()
            else -> super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}