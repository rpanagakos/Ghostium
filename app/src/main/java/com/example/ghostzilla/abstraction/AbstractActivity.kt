package com.example.ghostzilla.abstraction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class AbstractActivity<T : ViewDataBinding>(contentLayoutId: Int) :
    AppCompatActivity(contentLayoutId) {

    lateinit var binding: T

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initLayout()
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

    abstract fun stopOperation()

    override fun onDestroy() {
        super.onDestroy()
    }
}