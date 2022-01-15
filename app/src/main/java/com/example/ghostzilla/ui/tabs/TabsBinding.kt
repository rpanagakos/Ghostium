package com.example.ghostzilla.ui.tabs

import android.annotation.SuppressLint
import android.text.Html
import android.text.SpannableString
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.ghostzilla.R
import com.example.ghostzilla.models.errors.mapper.NOT_FOUND
import com.example.ghostzilla.utils.getSpannableText
import com.example.ghostzilla.utils.setTextViewLinkHtml
import com.google.android.material.tabs.TabLayout
import de.hdodenhof.circleimageview.CircleImageView
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ln
import kotlin.math.pow

@SuppressLint("SetTextI18n")
object TabsBinding {

    @BindingAdapter("addTabs")
    @JvmStatic
    fun TabLayout.addTabs(tabs: List<String>?) {
        if (this.tabCount > 0)
            return
        tabs?.forEach { tabsLabel ->
            this.apply {
                addTab(newTab())
                val tab = this.getTabAt(this.tabCount - 1)
                tab?.text = tabsLabel
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    @BindingAdapter("timestamp")
    @JvmStatic
    fun TextView.convertLongToDate(timestamp: Long) {
        val date = Date(timestamp)
        val format = SimpleDateFormat("dd MMMM, HH:mm", Locale.ENGLISH)
        this.text = format.format(date)
    }

    @BindingAdapter(value = ["imageURLContract"])
    @JvmStatic
    fun ImageView.loadImageFromUrl(imageUrl: String?) {
        if (imageUrl != null) {
            Glide.with(this)
                .load(imageUrl)
                .error(Glide.with(this).load(R.drawable.ic_launcher_foreground))
                .into(this)
        }
    }

    @BindingAdapter(value = ["rectangleImageUrl"])
    @JvmStatic
    fun ImageView.loadRectangleImage(imageUrl: String?) {
        if (imageUrl != null) {
            Glide.with(this.context)
                .load(imageUrl)
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
                .error(Glide.with(this).load(R.drawable.ic_launcher_foreground))
                .into(this)
        }
    }

    @BindingAdapter(value = ["circleImageUrl"])
    @JvmStatic
    fun ImageView.loadCircleImage(imageUrl: String?) {
        if (imageUrl != null) {
            Glide.with(this)
                .load(imageUrl)
                .circleCrop()
                .error(Glide.with(this).load(R.drawable.ic_launcher_foreground))
                .into(this)
        }
    }

    @BindingAdapter("cryptoPrice")
    @JvmStatic
    fun TextView.convertPrice(cryptoPrice: Double) {
        var dec = DecimalFormat("#,###.####")
        var roundedPrice = dec.format(cryptoPrice)
        if (roundedPrice.equals("0")) {
            dec = DecimalFormat("#,###.######")
            roundedPrice = dec.format(cryptoPrice)
        }
        val spannableInt = SpannableString("$roundedPrice €")
        text = getSpannableText(spannableInt, roundedPrice)
    }

    @BindingAdapter("marketCap")
    @JvmStatic
    fun TextView.marketCapFormatter(marketCap: Long) {
        if (marketCap < 1000) {
            this.text = "" + marketCap.toString()
            return
        }
        val exp = (ln(marketCap.toDouble()) / ln(1000.0)).toInt()
        val capValue = String.format(
            "%.1f %c",
            marketCap / 1000.0.pow(exp.toDouble()),
            "kMBTPE"[exp - 1]
        )
        val spannableInt = SpannableString(capValue)
        this.text = getSpannableText(spannableInt, capValue)
    }

    @BindingAdapter("convertToHtmlString")
    @JvmStatic
    fun TextView.convertToHtmlString(textDescription: String?) {
        if (textDescription == null)
            return
        else if (textDescription.isEmpty()) {
            this.text = context.getString(R.string.no_description)
            return
        }
        /* val dummy =
             "Bitcoin is the first successful internet money based on peer-to-peer technology; whereby no central bank or authority is involved in the transaction and production of the Bitcoin currency. It was created by an anonymous individual/group under the name, Satoshi Nakamoto. The source code is available publicly as an open source project, anybody can look at it and be part of the developmental process.\r\n\r\nBitcoin is changing the way we see money as we speak. The idea was to produce a means of exchange, independent of any central authority, that could be transferred electronically in a secure, verifiable and immutable way. It is a decentralized peer-to-peer internet currency making mobile payment easy, very low transaction fees, protects your identity, and it works anywhere all the time with no central authority and banks.\r\n\r\nBitcoin is designed to have only 21 million BTC ever created, thus making it a deflationary currency. Bitcoin uses the <a href=\"https://www.coingecko.com/en?hashing_algorithm=SHA-256\">SHA-256</a> hashing algorithm with an average transaction confirmation time of 10 minutes. Miners today are mining Bitcoin using ASIC chip dedicated to only mining Bitcoin, and the hash rate has shot up to peta hashes.\r\n\r\nBeing the first successful online cryptography currency, Bitcoin has inspired other alternative currencies such as <a href=\"https://www.coingecko.com/en/coins/litecoin\">Litecoin</a>, <a href=\"https://www.coingecko.com/en/coins/peercoin\">Peercoin</a>, <a href=\"https://www.coingecko.com/en/coins/primecoin\">Primecoin</a>, and so on.\r\n\r\nThe cryptocurrency then took off with the innovation of the turing-complete smart contract by <a href=\"https://www.coingecko.com/en/coins/ethereum\">Ethereum</a> which led to the development of other amazing projects such as <a href=\"https://www.coingecko.com/en/coins/eos\">EOS</a>, <a href=\"https://www.coingecko.com/en/coins/tron\">Tron</a>, and even crypto-collectibles such as <a href=\"https://www.coingecko.com/buzz/ethereum-still-king-dapps-cryptokitties-need-1-billion-on-eos\">CryptoKitties</a>."

       */
        val htmlBody = Html.fromHtml(textDescription, HtmlCompat.FROM_HTML_MODE_COMPACT)
        if (htmlBody.length > 150) {
            val link = "... <a href='showMore'><u>show more</u></a>."
            val displayedText = htmlBody.subSequence(0, 150).toString() + link

            val onClickCallback: ((Int, String) -> Unit) = { _, url ->
                when (url) {
                    "showMore" -> {
                        this.text = htmlBody
                    }
                }
            }
            this.setTextViewLinkHtml(displayedText, onClickCallback)
        } else
            this.text = htmlBody
    }

    @BindingAdapter("lottieStatus")
    @JvmStatic
    fun LottieAnimationView.playCustomAnimation(result: Int) {
        if (result == NOT_FOUND) {
            this.setAnimation("nothing_found.json")
        } else {
            this.setAnimation("internet_connection.json")
        }
        this.playAnimation()
    }

    @BindingAdapter("errorMessageStatus")
    @JvmStatic
    fun TextView.displayErrorMessage(result: Int) {
        if (result == NOT_FOUND) {
            this.text = resources.getString(R.string.nothing_found)
        } else {
            this.text = resources.getString(R.string.no_internet_connection)
        }
    }
}