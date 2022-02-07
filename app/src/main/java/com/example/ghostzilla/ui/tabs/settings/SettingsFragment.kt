package com.example.ghostzilla.ui.tabs.settings

import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ghostzilla.R
import com.example.ghostzilla.abstraction.AbstractFragment
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.database.security.DataStoreUtil
import com.example.ghostzilla.databinding.FragmentSettingsBinding
import com.example.ghostzilla.models.settings.AppOption
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.example.ghostzilla.models.settings.AppOption.SettingType
import com.example.ghostzilla.ui.tabs.common.TabsActivity
import android.content.Intent
import com.example.ghostzilla.di.CurrencyImpl
import com.example.ghostzilla.ui.tabs.settings.general.GeneralActivity


@AndroidEntryPoint
class SettingsFragment :
    AbstractFragment<FragmentSettingsBinding, SettingsViewModel>(R.layout.fragment_settings) {

    override val viewModel: SettingsViewModel by viewModels()

    @Inject
    lateinit var currencyImpl: CurrencyImpl

    override fun initLayout() {
        if ((requireActivity() as TabsActivity).languageChanged)
            requireActivity().recreate()

        val settingsList = listOf(
            AppOption(
                this.resources.getString(R.string.option_language),
                R.drawable.ic_baseline_info_24,
                AppOption.SettingType.LANGUAGE
            ),
            AppOption(
                this.resources.getString(R.string.option_currency),
                R.drawable.ic_baseline_info_24,
                AppOption.SettingType.CURRENCY
            ),
            AppOption(
                this.resources.getString(R.string.option_cryptos),
                R.drawable.ic_baseline_info_24,
                AppOption.SettingType.CRYPTO_FAV
            ),
            AppOption(
                this.resources.getString(R.string.option_articles),
                R.drawable.ic_baseline_info_24,
                AppOption.SettingType.NEWS_FAV
            ),
            AppOption(
                this.resources.getString(R.string.option_share),
                R.drawable.ic_baseline_info_24,
                AppOption.SettingType.SHARE_APP
            ),
            AppOption(
                this.resources.getString(R.string.option_rate),
                R.drawable.ic_baseline_info_24,
                AppOption.SettingType.RATE_APP
            ),
            AppOption(
                this.resources.getString(R.string.option_contact),
                R.drawable.ic_baseline_info_24,
                AppOption.SettingType.CONTACT_US
            ),
        )

        viewModel.runOperation(settingsList) { data: LocalModel,
                                               title: TextView ->
            when (data) {
                is AppOption -> {
                    when (data.type) {
                        SettingType.CRYPTO_FAV, SettingType.NEWS_FAV ->
                            findNavController().navigate(
                                SettingsFragmentDirections.settingsFavouriteAction(
                                    data.title
                                )
                            )
                        SettingType.LANGUAGE, SettingType.CURRENCY -> {
                            val i = Intent(requireContext(), GeneralActivity::class.java)
                            i.putExtra("language", if (data.type == SettingType.LANGUAGE) 0 else 1)
                            startActivity(i)
                            requireActivity().overridePendingTransition(
                                R.anim.slide_in_right,
                                R.anim.slide_out_left
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        currencyImpl.updateChosenCurrency()
    }

    override fun observeViewModel() {
    }

    override fun stopOperations() {
    }
}