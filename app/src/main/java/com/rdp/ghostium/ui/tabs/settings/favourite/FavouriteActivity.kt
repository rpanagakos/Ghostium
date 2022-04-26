package com.rdp.ghostium.ui.tabs.settings.favourite

import android.content.Intent
import android.widget.TextView
import androidx.activity.viewModels
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractActivity
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.databinding.ActivityFavouriteBinding
import com.rdp.ghostium.models.CryptoItemDB
import com.rdp.ghostium.models.coingecko.CryptoItem
import com.rdp.ghostium.models.coingecko.shimmer.CryptoShimmer
import com.rdp.ghostium.ui.tabs.common.TabsActivity
import com.rdp.ghostium.utils.BackToTopScrollListener
import com.rdp.ghostium.utils.appearWithCustomAnimation
import com.rdp.ghostium.utils.disappearWithCustomAnimation
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView

@AndroidEntryPoint
class FavouriteActivity : AbstractActivity<ActivityFavouriteBinding>(R.layout.activity_favourite) {

    val viewModel: FavouriteViewModel by viewModels()

    override fun initLayout() {
        observeViewModel()
        binding.title = this.resources.getString(R.string.option_cryptos)
        binding.viewModel = viewModel
        viewModel.favouriteAdapter.submitList(
            listOf(
                CryptoShimmer(),
                CryptoShimmer(),
                CryptoShimmer(),
                CryptoShimmer(),
                CryptoShimmer()
            )
        )
        binding.backButtonFavourite.setOnClickListener {
            onBackPressed()
        }
        binding.favCryptosRecycler.apply {
            setHasFixedSize(true)
            addOnScrollListener(object :
                BackToTopScrollListener(binding.backToTopImg.backToTopImg, context) {})
        }

        binding.share.optionConstraint.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, viewModel.getIntentText())
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
            viewModel.dismissEveryting()
        }
    }

    private fun observeViewModel() {
        viewModel.cryptos.observe(this, {
            viewModel.runOperation { data: LocalModel, title: TextView, subTitle: TextView?, circleImageView: CircleImageView ->
                when (data) {
                    is CryptoItemDB -> {
                        navigateToDetailsActivty(
                            CryptoItem(
                                id = data.id,
                                name = data.name,
                                symbol = data.symbol,
                                image = data.image
                            ), title, subTitle!!, circleImageView
                        )
                    }
                }
            }
        })
        viewModel.isProcessing.observe(this, { isProcessing ->
            if (isProcessing) {
                binding.menuLayout.appearWithCustomAnimation(R.anim.slide_up, this)
            } else {
                binding.checkbox.isChecked = false
                binding.menuLayout.disappearWithCustomAnimation(R.anim.slide_down, this)
            }
        })
    }

    override fun onBackPressed() {
        if (isTaskRoot)
            startActivity(Intent(this, TabsActivity::class.java))
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun runOperation() {}

    override fun stopOperation() {}

}