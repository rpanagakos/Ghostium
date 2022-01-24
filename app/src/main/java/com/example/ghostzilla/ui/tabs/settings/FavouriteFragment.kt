package com.example.ghostzilla.ui.tabs.settings

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
import com.example.ghostzilla.ui.tabs.settings.favourite.FavouriteViewModel
import com.example.ghostzilla.utils.BackToTopScrollListener
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
        backButtonFavourite.setOnClickListener { findNavController().popBackStack() }
        binding.favCryptosRecycler.apply {
            setHasFixedSize(true)
            addOnScrollListener(object :
                BackToTopScrollListener(binding.backToTopImg.backToTopImg, requireContext()) {})
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
    }

    override fun stopOperations() {
    }

}