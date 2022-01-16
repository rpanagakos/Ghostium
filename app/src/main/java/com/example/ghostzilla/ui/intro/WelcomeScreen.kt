package com.example.ghostzilla.ui.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import com.example.ghostzilla.R
import com.example.ghostzilla.models.onboarding.OnBoardingPage
import java.lang.reflect.Modifier

@Composable
fun WelcomeScreen(navHostController: NavHostController) {
    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )

    Column(modifier = androidx.compose.ui.Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.white))
    ) {

    }
}