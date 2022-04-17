package com.rdp.ghostium.ui.tabs.settings.favourite

import android.content.Intent
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractFragment
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.databinding.FragmentFavouriteBinding
import com.rdp.ghostium.models.CryptoItemDB
import com.rdp.ghostium.models.coingecko.CryptoItem
import com.rdp.ghostium.models.coingecko.shimmer.CryptoShimmer
import com.rdp.ghostium.ui.tabs.common.TabsActivity
import com.rdp.ghostium.utils.BackToTopScrollListener
import com.rdp.ghostium.utils.appearWithCustomAnimation
import com.rdp.ghostium.utils.disappearWithCustomAnimation
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_favourite.*

@AndroidEntryPoint
class FavouriteFragment :
    AbstractFragment<FragmentFavouriteBinding, FavouriteViewModel>(R.layout.fragment_favourite) {

    private val args: FavouriteFragmentArgs by navArgs()

    override val viewModel: FavouriteViewModel by viewModels()

    override fun initLayout() {
        binding.title = args.title
        viewModel.favouriteAdapter.submitList(listOf(CryptoShimmer(),CryptoShimmer(),CryptoShimmer(),CryptoShimmer(),CryptoShimmer()))
        backButtonFavourite.setOnClickListener {
            (requireActivity() as TabsActivity).showMenuBar()
            findNavController().popBackStack()
        }

        onBackPressed {
            (requireActivity() as TabsActivity).showMenuBar()
        }
        binding.favCryptosRecycler.apply {
            setHasFixedSize(true)
            addOnScrollListener(object :
                BackToTopScrollListener(binding.backToTopImg.backToTopImg, requireContext()) {})
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

    override fun observeViewModel() {
        viewModel.cryptos.observe(this, {
            viewModel.runOperation() { data: LocalModel, title: TextView, subTitle: TextView?, circleImageView: CircleImageView ->
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
                binding.menuLayout.appearWithCustomAnimation(R.anim.slide_up, requireContext())
            } else {
                binding.checkbox.isChecked = false
                binding.menuLayout.disappearWithCustomAnimation(R.anim.slide_down, requireContext())
            }
        })
    }

    override fun stopOperations() {
    }

}