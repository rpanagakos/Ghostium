package com.example.ghostzilla.ui.tabs.settings.favourite

import android.content.Intent
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.databinding.FragmentFavouriteBinding
import com.example.ghostzilla.models.CryptoItemDB
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.models.coingecko.shimmer.CryptoShimmer
import com.example.ghostzilla.ui.tabs.common.TabsActivity
import com.example.ghostzilla.utils.BackToTopScrollListener
import com.example.ghostzilla.utils.appearWithCustomAnimation
import com.example.ghostzilla.utils.disappearWithCustomAnimation
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