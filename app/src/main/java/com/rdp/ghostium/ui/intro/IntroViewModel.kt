package com.rdp.ghostium.ui.intro

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.rdp.ghostium.abstraction.AbstractViewModel
import com.rdp.ghostium.utils.Constants.Companion.SHARED_PREF
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class IntroViewModel @Inject constructor(
    application: Application
) : AbstractViewModel(application) {

    val preferences: SharedPreferences = context.getSharedPreferences(
        SHARED_PREF,
        Context.MODE_PRIVATE
    )


    fun saveOnBoardingStatus() {
        preferences.edit()
            .putBoolean("onBoarding", true)
            .apply()
    }

}
