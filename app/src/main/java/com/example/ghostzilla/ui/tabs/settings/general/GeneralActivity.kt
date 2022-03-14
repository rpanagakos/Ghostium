package com.example.ghostzilla.ui.tabs.settings.general

import androidx.activity.viewModels
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractActivity
import com.example.ghostzilla.databinding.ActivityGeneralBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_general.*


@AndroidEntryPoint
class GeneralActivity : AbstractActivity<ActivityGeneralBinding>(R.layout.activity_general) {

    val viewModel: GeneralSettingsViewModel by viewModels()
    val language_key = "language"

    override fun initLayout() {
        observeViewModel()
        intent.extras?.let {
            binding.language = it.getInt(language_key) == 0
            binding.title =
                if (it.getInt(language_key) == 0) getString(R.string.option_language) else getString(R.string.option_currency)
            binding.generalRecycler.adapter = viewModel.generalAdapter
            viewModel.runOperation(it.getInt(language_key) == 0)
        }
        backButtonFavourite.setOnClickListener {
            onBackPressed()
        }
    }

    private fun observeViewModel() {
        viewModel.langChanged.observe(this, {
            onBackPressed()
        })
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun runOperation() {
    }

    override fun stopOperation() {
    }


}