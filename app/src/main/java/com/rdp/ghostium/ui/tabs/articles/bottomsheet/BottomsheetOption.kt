package com.rdp.ghostium.ui.tabs.articles.bottomsheet

import android.content.Intent
import androidx.fragment.app.viewModels
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractBottomSheetDialogFragment
import com.rdp.ghostium.databinding.OptionsBottomSheetBinding
import com.rdp.ghostium.models.guardian.Article
import com.rdp.ghostium.utils.setSafeOnClickListener
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
        binding.secondOption.parentOption.setSafeOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, article.webUrl)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    override fun observeViewModel() {}
}
