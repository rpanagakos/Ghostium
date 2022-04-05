package com.example.ghostzilla.ui.tabs.articles.bottomsheet

import android.content.Intent
import androidx.fragment.app.viewModels
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractBottomSheetDialogFragment
import com.example.ghostzilla.databinding.OptionsBottomSheetBinding
import com.example.ghostzilla.models.guardian.Article
import com.example.ghostzilla.models.settings.AppOption
import com.example.ghostzilla.utils.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomsheetOption(val article: Article) :
    AbstractBottomSheetDialogFragment<OptionsBottomSheetBinding, BottomsheetArticlesViewModel>() {

    override val viewModel: BottomsheetArticlesViewModel by viewModels()

    override fun getLayoutRes(): Int = R.layout.options_bottom_sheet

    override fun initLayout() {
        binding.isLast = true
        binding.article = article
        viewModel.runOperation(article) { dismiss() }
    }

    override fun observeViewModel() {
        binding.secondOption.parentOption.setSafeOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, article.webUrl)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        viewModel.isSaved.observe(this, { isSaved ->
            if (isSaved)
                binding.optionOne = AppOption(
                    this.resources.getString(R.string.unsave_article),
                    R.drawable.ic_bookmark_filled,
                    AppOption.SettingType.SAVE_ARTICLE
                )
            else
                binding.optionOne = AppOption(
                    this.resources.getString(R.string.save_article),
                    R.drawable.ic_bookmark_light,
                    AppOption.SettingType.SAVE_ARTICLE
                )
            binding.optionSecond = AppOption(
                this.resources.getString(R.string.share_article),
                R.drawable.ic_share_light,
                AppOption.SettingType.SHARE_ARTICLE
            )
        })
    }
}
