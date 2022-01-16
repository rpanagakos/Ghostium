package com.example.ghostzilla.ui.intro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ghostzilla.ui.intro.navigation.setUpNavGraph
import com.example.ghostzilla.ui.intro.ui.theme.GhostzillaTheme

class IntroActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GhostzillaTheme {
                navHostController = rememberNavController()
                setUpNavGraph(navController = navHostController)

            }
        }
    }
}
