package com.example.ghostzilla.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AbstractActivity(R.layout.activity_main) {

    private lateinit var navController: NavController

    override fun initLayout() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.trendsFragment,
                R.id.walletFragment,
                R.id.profileFragment
            )
        )
        bottomNavigation.setupWithNavController(navController)
        observeViewModel()
    }

    private fun observeViewModel() {
    }

    override fun runOperation() {
    }

    override fun stopOperation() {
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}