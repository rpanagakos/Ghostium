package com.rdp.ghostium.ui.tabs.settings

import android.content.Intent
import android.net.Uri
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rdp.ghostium.BuildConfig
import com.rdp.ghostium.R
import com.rdp.ghostium.abstraction.AbstractFragment
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.databinding.FragmentSettingsBinding
import com.rdp.ghostium.di.common.CurrencyImpl
import com.rdp.ghostium.models.settings.AppOption
import com.rdp.ghostium.models.settings.AppOption.SettingType
import com.rdp.ghostium.models.settings.LogoOption
import com.rdp.ghostium.ui.tabs.common.TabsActivity
import com.rdp.ghostium.ui.tabs.settings.general.GeneralActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
            LogoOption(resources.getString(R.string.app_name), BuildConfig.VERSION_NAME),
            AppOption(
                this.resources.getString(R.string.option_language),
                R.drawable.ic_earth_europe,
                AppOption.SettingType.LANGUAGE
            ),
            AppOption(
                this.resources.getString(R.string.option_currency),
                R.drawable.ic_currencies,
                AppOption.SettingType.CURRENCY
            ),
            AppOption(
                this.resources.getString(R.string.option_cryptos),
                R.drawable.ic_saved_cryptos,
                AppOption.SettingType.CRYPTO_FAV
            ),
            AppOption(
                this.resources.getString(R.string.option_articles),
                R.drawable.ic_newspaper,
                AppOption.SettingType.NEWS_FAV
            ),
            AppOption(
                this.resources.getString(R.string.option_share),
                R.drawable.ic_share,
                AppOption.SettingType.SHARE_APP
            ),
            AppOption(
                this.resources.getString(R.string.option_rate),
                R.drawable.ic_rate,
                AppOption.SettingType.RATE_APP
            ),
            AppOption(
                this.resources.getString(R.string.option_contact),
                R.drawable.ic_contact_us,
                AppOption.SettingType.CONTACT_US
            ),
        )

        viewModel.runOperation(settingsList) { data: LocalModel,
                                               title: TextView ->
            when (data) {
                is AppOption -> {
                    when (data.type) {
                        SettingType.CRYPTO_FAV -> {
                            findNavController().navigate(
                                SettingsFragmentDirections.settingsFavouriteAction(
                                    data.title
                                )
                            )
                            (requireActivity() as TabsActivity).hideMenuBar()
                        }
                        SettingType.NEWS_FAV -> {
                            findNavController().navigate(
                                SettingsFragmentDirections.settingsBookmarkAction(
                                    data.title
                                )
                            )
                            (requireActivity() as TabsActivity).hideMenuBar()
                        }
                        SettingType.LANGUAGE, SettingType.CURRENCY -> {
                            val i = Intent(requireContext(), GeneralActivity::class.java)
                            i.putExtra("language", if (data.type == SettingType.LANGUAGE) 0 else 1)
                            startActivity(i)
                            requireActivity().overridePendingTransition(
                                R.anim.slide_in_right,
                                R.anim.slide_out_left
                            )
                        }
                        SettingType.CONTACT_US -> {
                            val intent = Intent(Intent.ACTION_SENDTO)
                            intent.data = Uri.parse("mailto:")
                            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("support@ghostzilla.com"))
                            startActivity(intent)
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