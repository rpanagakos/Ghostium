package com.example.ghostzilla.ui.intro

import androidx.fragment.app.viewModels
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.databinding.FragmentLoginBinding
import com.example.ghostzilla.ui.tabs.trends.TrendsViewModel


class LoginFragment : AbstractFragment<FragmentLoginBinding, TrendsViewModel>(R.layout.fragment_login) {

    override val viewModel: TrendsViewModel by viewModels()

    override fun initLayout() {}

    override fun observeViewModel() {}

    override fun stopOperations() {}

}