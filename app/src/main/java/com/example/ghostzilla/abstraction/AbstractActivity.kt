package com.example.ghostzilla.abstraction

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.ghostzilla.utils.LangContextWrapper

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
        runOperation()
    }

    abstract fun runOperation()

    override fun onStop() {
        stopOperation()
        super.onStop()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LangContextWrapper.wrap(newBase, "de"))
    }


    abstract fun stopOperation()

    override fun onDestroy() {
        super.onDestroy()
    }
}