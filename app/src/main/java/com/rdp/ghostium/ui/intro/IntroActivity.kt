package com.rdp.ghostium.ui.intro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rdp.ghostium.ui.intro.navigation.setUpNavGraph
import com.rdp.ghostium.ui.intro.ui.theme.GhostzillaTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : ComponentActivity() {

    private val viewModel: IntroViewModel by viewModels()

    private lateinit var navHostController: NavHostController

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GhostzillaTheme {
                navHostController = rememberNavController()
                setUpNavGraph(navController = navHostController)

            }
        }
    }

    override fun onBackPressed() {}

}
