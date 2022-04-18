package com.rdp.ghostium.ui.tabs.settings.bookmark

import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractFragment
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.databinding.FragmentBookmarkBinding
import com.rdp.ghostium.models.guardian.Article
import com.rdp.ghostium.ui.tabs.common.TabsActivity
import com.rdp.ghostium.utils.BackToTopScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favourite.*


@AndroidEntryPoint
class BookmarkFragment :
    AbstractFragment<FragmentBookmarkBinding, BookmarkViewModel>(R.layout.fragment_bookmark) {

    override val viewModel: BookmarkViewModel by viewModels()

    override fun initLayout() {
        binding.title = requireContext().resources.getString(R.string.option_articles)
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