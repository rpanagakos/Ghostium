package com.rdp.ghostium

import android.app.Application
import android.content.Context
import com.rdp.ghostium.utils.LangContextWrapper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GhostiumApplication : Application() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LangContextWrapper.wrap(newBase))
    }
}