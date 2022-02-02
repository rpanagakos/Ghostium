package com.example.ghostzilla

import android.app.Application
import android.content.Context
import com.example.ghostzilla.utils.LangContextWrapper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GhostzillaApplication : Application() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LangContextWrapper.wrap(newBase))
    }
}