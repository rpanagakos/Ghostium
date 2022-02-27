package com.example.ghostzilla.models.onboarding

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.example.ghostzilla.R
import org.junit.runner.Description

sealed class OnBoardingPage(
    val imageView: LottieCompositionSpec,
    val title: String,
    val description: String
){
    object First: OnBoardingPage(
        imageView = LottieCompositionSpec.RawRes(R.raw.first_intro_tab),
        title = "Greetings",
        description = "Are you a fan of cryptos and nfts? If you are then we have great news for you!"
    )
    object Second: OnBoardingPage(
        imageView = LottieCompositionSpec.RawRes(R.raw.first_intro_tab),
        title = "Explore",
        description = "Find your favourite cryptos and nfts, explore their news, prices and uniqueness to gain profit. "
    )
    object Third: OnBoardingPage(
        imageView = LottieCompositionSpec.RawRes(R.raw.rocket),
        title = "Power",
        description = "By using this app you have all the information you need in your daily bases."
    )
}
