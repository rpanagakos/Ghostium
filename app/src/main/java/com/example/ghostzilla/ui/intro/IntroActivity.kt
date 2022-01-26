package com.example.ghostzilla.ui.intro

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ghostzilla.ui.intro.navigation.setUpNavGraph
import com.example.ghostzilla.ui.intro.ui.theme.GhostzillaTheme
import com.example.ghostzilla.ui.tabs.common.TabsActivity
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : ComponentActivity() {

    private val viewModel: IntroViewModel by viewModels()

    private lateinit var navHostController: NavHostController

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (viewModel.preferences.getBoolean("onBoarding", false)) {
            startActivity(Intent(this, TabsActivity::class.java))
        } else {
            setContent {
                GhostzillaTheme {
                    navHostController = rememberNavController()
                    setUpNavGraph(navController = navHostController)

                }
            }
        }
    }

}
