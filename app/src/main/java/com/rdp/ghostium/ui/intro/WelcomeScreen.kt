package com.rdp.ghostium.ui.intro

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.rdp.ghostium.R
import com.rdp.ghostium.models.onboarding.OnBoardingPage
import com.rdp.ghostium.ui.intro.ui.theme.Shapes
import com.rdp.ghostium.ui.tabs.common.TabsActivity
import com.google.accompanist.pager.*

@ExperimentalPagerApi
@Composable
fun WelcomeScreen(
    navHostController: NavHostController,
    introViewModel: IntroViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val ON_BOARDING_PAGE_COUNT = 3

    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )

    val pagerState = rememberPagerState()

    Column(
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {

        HorizontalPager(
            modifier = Modifier.weight(10f),
            state = pagerState,
            count = ON_BOARDING_PAGE_COUNT,
            verticalAlignment = Alignment.Top
        ) { position ->
            PagerScreen(page = pages[position])

        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            pagerState = pagerState,
            activeColor = colorResource(id = R.color.Black3),
            inactiveColor = colorResource(id = R.color.CoolGray),
            indicatorWidth = 16.dp,
            spacing = 8.dp
        )
        FinishButton(
            modifier = Modifier.weight(2f),
            pagerState = pagerState
        ) {
            introViewModel.saveOnBoardingStatus()
            context.startActivity(Intent(context, TabsActivity::class.java))
        }
    }
}

@Composable
fun PagerScreen(page: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val composition by rememberLottieComposition(page.imageView)
        LottieAnimation(
            composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.fillMaxWidth()
                .height(300.dp)
        )
        Text(
            text = page.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1

        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(vertical = 8.dp),
            text = page.description,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1
        )
    }

}


@ExperimentalPagerApi
@Composable
fun FinishButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == 2
        ) {
            Button(
                onClick = onClick,
                shape = Shapes.large,
                modifier = Modifier.height(54.dp)
            ) {
                Text(
                    text = "Finish",
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}