package com.example.ghostzilla.ui.tabs.articles

import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractActivity
import com.example.ghostzilla.databinding.ActivityArticleDetailsBinding
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.models.guardian.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*

@AndroidEntryPoint
class ArticleDetailsActivity :
    AbstractActivity<ActivityArticleDetailsBinding>(R.layout.activity_article_details) {

    private var article: Article? = null

    override fun initLayout() {
        article = intent.getParcelableExtra("article")
        when (article) {
            null -> onBackPressed()
            else -> {
                binding.article = article
            }
        }
    }

    override fun runOperation() {
        backButton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    override fun stopOperation() {
    }
}