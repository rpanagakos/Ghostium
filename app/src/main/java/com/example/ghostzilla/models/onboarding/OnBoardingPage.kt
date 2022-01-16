package com.example.ghostzilla.models.onboarding

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.example.ghostzilla.R
import org.junit.runner.Description

sealed class OnBoardingPage(
    @DrawableRes
    val imageView: Int,
    val title: String,
    val description: String
){
    object First: OnBoardingPage(
        imageView = R.drawable.ic_twitter,
        title = "Greetings",
        description = "Are you a fan of cryptos and nfts? If you are then we have great news for you!"
    )
    object Second: OnBoardingPage(
        imageView = R.drawable.ic_twitter,
        title = "Explore",
        description = "Find your favourite cryptos and nfts, explore their news, prices and uniqueness to gain profit. "
    )
    object Third: OnBoardingPage(
        imageView = R.drawable.ic_googlechrome,
        title = "Power",
        description = "By using this app you have all the information you need in your daily bases."
    )
}
