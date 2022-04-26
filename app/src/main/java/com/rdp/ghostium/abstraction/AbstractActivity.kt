package com.rdp.ghostium.abstraction

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.rdp.ghostium.R
import com.rdp.ghostium.models.coingecko.CryptoItem
import com.rdp.ghostium.models.guardian.Article
import com.rdp.ghostium.ui.tabs.articles.ArticleDetailsActivity
import com.rdp.ghostium.ui.tabs.articles.bottomsheet.BottomsheetOption
import com.rdp.ghostium.ui.tabs.cryptos.DetailsActivity
import com.rdp.ghostium.utils.LangContextWrapper

abstract class AbstractActivity<T : ViewDataBinding>(private val contentLayoutId: Int) :
    AppCompatActivity() {

    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, contentLayoutId)
        binding.lifecycleOwner = this
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initLayout()
        binding.executePendingBindings()
    }

    abstract fun initLayout()

    override fun onPostResume() {
        super.onPostResume()
        if (this.resources.configuration.locale.language == LangContextWrapper.getSavedLang(this))
            runOperation()
        else
            this.recreate()
    }

    abstract fun runOperation()

    override fun onStop() {
        stopOperation()
        super.onStop()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LangContextWrapper.wrap(newBase))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        (intent.extras ?: Bundle()).also {
            it.putAll(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("Language", this.resources.configuration.locale.language)
        super.onSaveInstanceState(outState)
    }

    abstract fun stopOperation()

    fun navigateToDetailsActivty(data: CryptoItem, title: View, subTitle: View, image: ImageView) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra("coin", data)
        }
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            Pair.create(
                title, getString(R.string.transition_coin_name)
            ),
            Pair.create(
                subTitle, getString(R.string.transition_coin_symbol)
            ),
            Pair.create(
                image, getString(R.string.transition_coin_image)
            )
        )
        startActivity(intent, options.toBundle())
        this.window.exitTransition = null
    }

    fun navigateToArticlesActivty(data : Article, title : View, subTitle : View, image : ImageView){
        val intent = Intent(this, ArticleDetailsActivity::class.java).apply {
            putExtra("article", data)
        }
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            Pair.create(
                title, getString(R.string.transition_article_title)
            ),
            Pair.create(
                subTitle, getString(R.string.transition_article_date)
            ),
            Pair.create(
                image, getString(R.string.transition_article_image)
            )
        )
        startActivity(intent, options.toBundle())
        this.window.exitTransition = null
    }

    fun openBottomsheetOptions(data: LocalModel) {
        when (data) {
            is Article -> {
                val bottomsheetOption = BottomsheetOption(data)
                bottomsheetOption.show(supportFragmentManager, bottomsheetOption.tag)
            }
        }
    }
}