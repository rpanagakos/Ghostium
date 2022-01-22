package com.example.ghostzilla.ui.tabs.profile

import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.database.security.DataStoreUtil
import com.example.ghostzilla.databinding.FragmentProfileBinding
import com.example.ghostzilla.models.account.OptionItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment :
    AbstractFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {

    override val viewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var dataStore: DataStoreUtil

    override fun initLayout() {
        viewModel.runOperation() { data: LocalModel,
                                   title: TextView ->
            when (data) {
                is OptionItem -> {
                    findNavController().navigate(ProfileFragmentDirections.optionDetailsAction(data.title))
                }
            }
        }
    }

    override fun observeViewModel() {
    }

    override fun stopOperations() {
    }
}