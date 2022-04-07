package com.example.ghostzilla.ui.tabs.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractActivity
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.databinding.ActivityMainBinding
import com.example.ghostzilla.models.guardian.Article
import com.example.ghostzilla.ui.intro.IntroActivity
import com.example.ghostzilla.ui.tabs.articles.ArticlesFragment
import com.example.ghostzilla.ui.tabs.articles.bottomsheet.BottomsheetOption
import com.example.ghostzilla.ui.tabs.cryptos.TrendsFragment
import com.example.ghostzilla.ui.tabs.cryptos.TrendsViewModel
import com.example.ghostzilla.ui.tabs.nft.NftsFragment
import com.example.ghostzilla.ui.tabs.settings.SettingsFragment
import com.example.ghostzilla.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class TabsActivity : AbstractActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController
    var languageChanged = false
    val viewModel: TabsViewModel by viewModels()
    val viewModel2: TrendsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        val hasSeenIntro = this.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE)
            .getBoolean("onBoarding", false)
        if (!hasSeenIntro)
            startActivity(Intent(this, IntroActivity::class.java))
        super.onCreate(savedInstanceState)
    }

    override fun initLayout() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)
        binding.viewModel = viewModel
        binding.bottomNavigation.setupWithNavController(navController)
    }

    override fun runOperation() {
        viewModel.trendingCryptos.observe(this, {
            viewModel.runOperation()
        })
    }

    override fun stopOperation() {}

    fun hideMenuBar() {
        binding.bottomNavigation.visibility = View.GONE
    }

    fun showMenuBar() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }

    fun openBottomsheetOptions(data: LocalModel) {
        when (data) {
            is Article -> {
                val bottomsheetOption = BottomsheetOption(data)
                bottomsheetOption.show(supportFragmentManager, bottomsheetOption.tag)
            }
        }
    }

    override fun onBackPressed() {
        when (nav_host_fragment_container.childFragmentManager.fragments[0]) {
            is ArticlesFragment, is TrendsFragment,
            is NftsFragment, is SettingsFragment -> finishAffinity()
            else -> {

                super.onBackPressed()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}