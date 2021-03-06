package com.rdp.ghostium.ui.tabs.settings.bookmark

import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.paging.PagingData
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractActivity
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.databinding.ActivityBookmarkBinding
import com.rdp.ghostium.models.guardian.Article
import com.rdp.ghostium.ui.tabs.common.TabsActivity
import com.rdp.ghostium.utils.BackToTopScrollListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BookmarkActivity : AbstractActivity<ActivityBookmarkBinding>(R.layout.activity_bookmark) {

    val viewModel: BookmarkViewModel by viewModels()

    override fun initLayout() {
        binding.viewModel = viewModel
        binding.title = this.resources.getString(R.string.option_articles)
        binding.backButtonFavourite.setOnClickListener {
            onBackPressed()
        }

        binding.articlesRecyclerView.apply {
            setHasFixedSize(true)
            addOnScrollListener(object :
                BackToTopScrollListener(
                    binding.backToTopImg.backToTopImg,
                    this@BookmarkActivity
                ) {})
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.articles.observe(this, {
            viewModel.runOperation(PagingData.from(it)) { data: LocalModel, title: TextView?, subTitle: TextView?, imageView: ImageView? ->
                when (data) {
                    is Article -> {
                        if (title == null)
                            openBottomsheetOptions(data)
                        else if (subTitle != null && imageView != null)
                            navigateToArticlesActivty(data, title, subTitle, imageView)
                    }
                }
            }
        })
    }

    override fun onBackPressed() {
        if (isTaskRoot)
            startActivity(Intent(this, TabsActivity::class.java))
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun runOperation() {
    }

    override fun stopOperation() {
    }

}