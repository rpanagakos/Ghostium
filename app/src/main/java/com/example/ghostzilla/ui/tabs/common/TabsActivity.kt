package com.example.ghostzilla.ui.tabs.common

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractActivity
import com.example.ghostzilla.databinding.ActivityMainBinding
import com.example.ghostzilla.ui.intro.IntroActivity
import com.example.ghostzilla.ui.tabs.home.HomeFragment
import com.example.ghostzilla.ui.tabs.settings.SettingsFragment
import com.example.ghostzilla.ui.tabs.trends.TrendsFragment
import com.example.ghostzilla.ui.tabs.nft.NftsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class TabsActivity : AbstractActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController
    var languageChanged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val hasSeenIntro = this.getSharedPreferences("onBoarding", Context.MODE_PRIVATE).getBoolean("onBoarding", false)
        if (!hasSeenIntro)
            startActivity(Intent(this, IntroActivity::class.java))
        super.onCreate(savedInstanceState)
    }

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
            is NftsFragment, is SettingsFragment -> finishAffinity()
            else -> super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}