package com.example.ghostzilla.abstraction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class AbstractFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        observeViewModel()
    }

    abstract fun initLayout()

    abstract fun observeViewModel()

    override fun onPause() {
        super.onPause()
        stopOperations()
    }

    abstract fun stopOperations()
}