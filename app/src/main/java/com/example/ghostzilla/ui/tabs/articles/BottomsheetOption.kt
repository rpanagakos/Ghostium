package com.example.ghostzilla.ui.tabs.articles

import android.content.Intent
import androidx.fragment.app.viewModels
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractBottomSheetDialogFragment
import com.example.ghostzilla.databinding.OptionsBottomSheetBinding
import com.example.ghostzilla.models.guardian.Article
import com.example.ghostzilla.models.settings.AppOption
import com.example.ghostzilla.ui.tabs.cryptos.TrendsViewModel
import com.example.ghostzilla.utils.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomsheetOption(val article: Article) :
    AbstractBottomSheetDialogFragment<OptionsBottomSheetBinding, TrendsViewModel>() {

    override val viewModel: TrendsViewModel by viewModels()

    override fun getLayoutRes(): Int = R.layout.options_bottom_sheet

    override fun initLayout() {
        binding.isLast = true
        binding.optionSecond = AppOption(
            "Share Article",
            R.drawable.ic_share_light,
            AppOption.SettingType.SHARE_ARTICLE
        )
        binding.optionOne = AppOption(
            "Save Article",
            R.drawable.ic_bookmark_light,
            AppOption.SettingType.SAVE_ARTICLE
        )
    }

    override fun observeViewModel() {
        binding.secondOption.parentOption.setSafeOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, article.shortUrl)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }
}
