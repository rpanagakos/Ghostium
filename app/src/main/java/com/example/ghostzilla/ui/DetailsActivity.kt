package com.example.ghostzilla.ui

import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AbstractActivity(R.layout.activity_details) {

    override fun initLayout() {
        observeViewModel()
    }

    private fun observeViewModel() {
    }

    override fun runOperation() {
    }

    override fun stopOperation() {
    }

}