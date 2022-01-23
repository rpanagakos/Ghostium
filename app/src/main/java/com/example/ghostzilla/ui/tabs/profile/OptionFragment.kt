package com.example.ghostzilla.ui.tabs.profile

import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.databinding.FragmentOptionBinding
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.ui.tabs.profile.favourite.FavouriteViewModel
import com.example.ghostzilla.utils.BackToTopScrollListener
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_option.*

@AndroidEntryPoint
class OptionFragment :
    AbstractFragment<FragmentOptionBinding, FavouriteViewModel>(R.layout.fragment_option) {

    private val args: OptionFragmentArgs by navArgs()

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
                    is CryptoItem -> {
                        navigateToDetailsActivty(data, title, subTitle!!, circleImageView)
                    }
                }
            }
        })
    }

    override fun stopOperations() {
    }

}