package com.example.ghostzilla.ui.tabs.articles

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ghostzilla.R
import com.example.ghostzilla.databinding.OptionsBottomSheetBinding
import com.example.ghostzilla.models.guardian.Article
import com.example.ghostzilla.models.settings.AppOption
import com.example.ghostzilla.utils.setSafeOnClickListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomsheetOption(val article: Article) : BottomSheetDialogFragment() {

    private lateinit var binding: OptionsBottomSheetBinding

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OptionsBottomSheetBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
