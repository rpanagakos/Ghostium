package com.example.ghostzilla.ui.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment


class ProfileFragment : AbstractFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun initLayout() {
    }

    override fun observeViewModel() {
    }

    override fun stopOperations() {
    }
}