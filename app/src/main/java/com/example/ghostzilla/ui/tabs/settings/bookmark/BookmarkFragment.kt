package com.example.ghostzilla.ui.tabs.settings.bookmark

import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.databinding.FragmentBookmarkBinding
import com.example.ghostzilla.models.guardian.Article
import com.example.ghostzilla.ui.tabs.common.TabsActivity
import com.example.ghostzilla.utils.BackToTopScrollListener
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

        binding.articlesRecyclerView.apply {
            setHasFixedSize(true)
            addOnScrollListener(object :
                BackToTopScrollListener(binding.backToTopImg.backToTopImg, requireContext()) {})
        }
    }

    override fun observeViewModel() {
        viewModel.articles.observe(this, {
            viewModel.runOperation(PagingData.from(it)) { data: LocalModel, title: TextView?, subTitle: TextView?, imageView: ImageView? ->
                when (data) {
                    is Article -> {
                        if (title == null)
                            (requireActivity() as TabsActivity).openBottomsheetOptions(data)
                        else if (subTitle != null && imageView != null)
                            navigateToArticlesActivty(data, title, subTitle, imageView)
                    }
                }
            }
        })
    }

    override fun stopOperations() {}

}