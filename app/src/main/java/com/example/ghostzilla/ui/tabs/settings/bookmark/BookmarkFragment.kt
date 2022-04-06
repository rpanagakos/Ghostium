package com.example.ghostzilla.ui.tabs.settings.bookmark

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.databinding.FragmentBookmarkBinding
import com.example.ghostzilla.ui.tabs.common.TabsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favourite.*


@AndroidEntryPoint
class BookmarkFragment :
    AbstractFragment<FragmentBookmarkBinding, BookmarkViewModel>(R.layout.fragment_bookmark) {

    override val viewModel: BookmarkViewModel by viewModels()
    private val args: BookmarkFragmentArgs by navArgs()


    override fun initLayout() {
        binding.title = args.title
        onBackPressed {
            (requireActivity() as TabsActivity).showMenuBar()
        }
        backButtonFavourite.setOnClickListener {
            (requireActivity() as TabsActivity).showMenuBar()
            findNavController().popBackStack()
        }
    }

    override fun observeViewModel() {}

    override fun stopOperations() {}

}