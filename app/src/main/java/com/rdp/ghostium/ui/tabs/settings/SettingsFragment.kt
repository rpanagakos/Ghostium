package com.rdp.ghostium.ui.tabs.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.TextView
import androidx.fragment.app.viewModels
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
import com.rdp.ghostium.ui.tabs.settings.bookmark.BookmarkActivity
import com.rdp.ghostium.ui.tabs.settings.favourite.FavouriteActivity
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
                SettingType.LANGUAGE
            ),
            AppOption(
                this.resources.getString(R.string.option_currency),
                R.drawable.ic_currencies,
                SettingType.CURRENCY
            ),
            AppOption(
                this.resources.getString(R.string.option_cryptos),
                R.drawable.ic_saved_cryptos,
                SettingType.CRYPTO_FAV
            ),
            AppOption(
                this.resources.getString(R.string.option_articles),
                R.drawable.ic_newspaper,
                SettingType.NEWS_FAV
            ),
            AppOption(
                this.resources.getString(R.string.option_share),
                R.drawable.ic_share,
                SettingType.SHARE_APP
            ),
            AppOption(
                this.resources.getString(R.string.option_rate),
                R.drawable.ic_rate,
                SettingType.RATE_APP
            )
        )

        viewModel.runOperation(settingsList) { data: LocalModel,
                                               title: TextView ->
            when (data) {
                is AppOption -> {
                    when (data.type) {
                        SettingType.CRYPTO_FAV -> {
                            val i = Intent(requireContext(), FavouriteActivity::class.java)
                            startActivity(i)
                            requireActivity().overridePendingTransition(
                                R.anim.slide_in_right,
                                R.anim.slide_out_left
                            )
                        }
                        SettingType.NEWS_FAV -> {
                            val i = Intent(requireContext(), BookmarkActivity::class.java)
                            startActivity(i)
                            requireActivity().overridePendingTransition(
                                R.anim.slide_in_right,
                                R.anim.slide_out_left
                            )
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
                        SettingType.SHARE_APP -> {
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Join the simpliest world of cryptos.\nGhostium is one click away...\nhttps://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
                                )
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            startActivity(shareIntent)
                        }
                        SettingType.RATE_APP -> {
                            try {
                                startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("market://details?id=${BuildConfig.APPLICATION_ID}")
                                    )
                                )
                            } catch (e: ActivityNotFoundException) {
                                startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
                                    )
                                )
                            }
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

    override fun observeViewModel() {}

    override fun stopOperations() {}
}