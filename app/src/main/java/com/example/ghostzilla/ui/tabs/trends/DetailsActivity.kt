package com.example.ghostzilla.ui.tabs.trends

import androidx.activity.viewModels
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractActivity
import com.example.ghostzilla.databinding.ActivityDetailsBinding
import com.example.ghostzilla.models.coingecko.CryptoItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*

@AndroidEntryPoint
class DetailsActivity : AbstractActivity<ActivityDetailsBinding>(R.layout.activity_details) {

    private var cryptoItem: CryptoItem? = null
    private val viewModel: DetailsViewModel by viewModels()

    override fun initLayout() {
        cryptoItem = intent.getParcelableExtra("coin")
        when (cryptoItem) {
            null -> onBackPressed()
            else -> {
                binding.viewModel = viewModel
                binding.crypto = cryptoItem
                viewModel.runOperation(cryptoItem!!.id)
            }
        }
    }

    override fun runOperation() {
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun stopOperation() {}

}