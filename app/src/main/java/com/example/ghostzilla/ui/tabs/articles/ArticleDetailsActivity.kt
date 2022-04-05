package com.example.ghostzilla.ui.tabs.articles

import androidx.activity.viewModels
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractActivity
import com.example.ghostzilla.databinding.ActivityArticleDetailsBinding
import com.example.ghostzilla.models.guardian.Article
import com.example.ghostzilla.ui.tabs.cryptos.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*

@AndroidEntryPoint
class ArticleDetailsActivity :
    AbstractActivity<ActivityArticleDetailsBinding>(R.layout.activity_article_details) {

    private var article: Article? = null
    private val viewModel: DetailsViewModel by viewModels()

    override fun initLayout() {
        article = intent.getParcelableExtra("article")
        when (article) {
            null -> onBackPressed()
            else -> {
                binding.article = article
            }
        }
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun runOperation() {
        backButton.setOnClickListener { onBackPressed() }
    }

    override fun stopOperation() {
    }
}