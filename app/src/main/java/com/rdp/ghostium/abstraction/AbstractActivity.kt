package com.rdp.ghostium.abstraction

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.rdp.ghostium.utils.LangContextWrapper

abstract class AbstractActivity<T : ViewDataBinding>(private val contentLayoutId: Int) :
    AppCompatActivity() {

    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, contentLayoutId)
        binding.lifecycleOwner = this
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initLayout()
        binding.executePendingBindings()
    }

    abstract fun initLayout()

    override fun onPostResume() {
        super.onPostResume()
        if (this.resources.configuration.locale.language == LangContextWrapper.getSavedLang(this))
            runOperation()
        else
            this.recreate()
    }

    abstract fun runOperation()

    override fun onStop() {
        stopOperation()
        super.onStop()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LangContextWrapper.wrap(newBase))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        (intent.extras ?: Bundle()).also {
            it.putAll(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("Language", this.resources.configuration.locale.language)
        super.onSaveInstanceState(outState)
    }

    abstract fun stopOperation()

    override fun onDestroy() {
        super.onDestroy()
    }
}