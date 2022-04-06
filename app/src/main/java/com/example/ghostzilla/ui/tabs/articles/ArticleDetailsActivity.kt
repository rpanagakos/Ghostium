package com.example.ghostzilla.ui.tabs.articles

import android.content.Intent
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
    private val viewModel: ArticleDetailsViewModel by viewModels()

    override fun initLayout() {
        article = intent.getParcelableExtra("article")
        when (article) {
            null -> onBackPressed()
            else -> {
                binding.viewModel = viewModel
                binding.article = article
                viewModel.runOperation(article!!){
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, it.webUrl)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                }
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