package com.example.ghostzilla.abstraction

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.fragment.findNavController
import com.example.ghostzilla.R
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.ui.tabs.cryptos.DetailsActivity

abstract class AbstractFragment<T : ViewDataBinding, VM : ViewModel>(contentLayoutId: Int) :
    Fragment(contentLayoutId) {

    lateinit var binding: T
    abstract val viewModel : VM


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = DataBindingUtil.bind(view)!!
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        observeViewModel()
        binding.setVariable(BR.viewModel, viewModel )
        binding.lifecycleOwner = viewLifecycleOwner
        //merge recyclerview
        //include to toolbar
        binding.executePendingBindings()
    }

    abstract fun initLayout()

    abstract fun observeViewModel()

    override fun onPause() {
        stopOperations()
        super.onPause()
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }

    abstract fun stopOperations()

    protected fun onBackPressed(click :() -> Unit){
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                click.invoke()
                findNavController().popBackStack()
            }
        })
    }

    fun navigateToDetailsActivty(data : CryptoItem, title : View, subTitle : View, image : ImageView){
        val intent = Intent(requireActivity(), DetailsActivity::class.java).apply {
            putExtra("coin", data)
        }
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            Pair.create(
                title, getString(R.string.transition_coin_name)
            ),
            Pair.create(
                subTitle, getString(R.string.transition_coin_symbol)
            ),
            Pair.create(
                image, getString(R.string.transition_coin_image)
            )
        )
        startActivity(intent, options.toBundle())
        requireActivity().window.exitTransition = null
    }
}