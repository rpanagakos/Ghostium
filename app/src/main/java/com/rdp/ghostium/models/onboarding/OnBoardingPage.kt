package com.rdp.ghostium.models.onboarding

import com.airbnb.lottie.compose.LottieCompositionSpec
import com.rdp.ghostium.R

sealed class OnBoardingPage(
    val imageView: LottieCompositionSpec,
    val title: String,
    val description: String
){
    object First: OnBoardingPage(
        imageView = LottieCompositionSpec.RawRes(R.raw.hello_lottie),
        title = "Greetings",
        description = "Are you a fan of cryptos? If you are then we have great news for you!"
    )
    object Second: OnBoardingPage(
        imageView = LottieCompositionSpec.RawRes(R.raw.calm_lottie),
        title = "Explore",
        description = "Find your favourite cryptos, explore their news, prices and uniqueness to gain profit. "
    )
    object Third: OnBoardingPage(
        imageView = LottieCompositionSpec.RawRes(R.raw.rocket),
        title = "Power",
        description = "By using this app you have all the information you need in your daily bases."
    )
}
