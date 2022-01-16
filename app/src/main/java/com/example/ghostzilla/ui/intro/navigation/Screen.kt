package com.example.ghostzilla.ui.intro.navigation

sealed class Screen(val route : String) {
    object Welcome: Screen("welcome_screen")
}