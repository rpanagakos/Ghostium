package com.example.ghostzilla.ui.tabs.settings.bookmark

import androidx.fragment.app.viewModels
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.databinding.FragmentBookmarkBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BookmarkFragment :
    AbstractFragment<FragmentBookmarkBinding, BookmarkViewModel>(R.layout.fragment_bookmark) {

    override val viewModel: BookmarkViewModel by viewModels()

    override fun initLayout() {
    }

    override fun observeViewModel() {
    }

    override fun stopOperations() {
    }


}