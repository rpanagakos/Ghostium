package com.rdp.ghostium.ui.intro.navigation

sealed class Screen(val route : String) {
    object Welcome: Screen("welcome_screen")
}