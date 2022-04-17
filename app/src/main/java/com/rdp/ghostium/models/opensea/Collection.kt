package com.rdp.ghostium.models.opensea


import com.google.gson.annotations.SerializedName

data class Collection(
    @SerializedName("banner_image_url")
    val bannerImageUrl: String,
    @SerializedName("chat_url")
    val chatUrl: Any,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("default_to_fiat")
    val defaultToFiat: Boolean,
    @SerializedName("description")
    val description: String,
    @SerializedName("dev_buyer_fee_basis_points")
    val devBuyerFeeBasisPoints: String,
    @SerializedName("dev_seller_fee_basis_points")
    val devSellerFeeBasisPoints: String,
    @SerializedName("discord_url")
    val discordUrl: String,
    @SerializedName("display_data")
    val displayData: DisplayData,
    @SerializedName("external_url")
    val externalUrl: String,
    @SerializedName("featured")
    val featured: Boolean,
    @SerializedName("featured_image_url")
    val featuredImageUrl: String,
    @SerializedName("hidden")
    val hidden: Boolean,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("instagram_username")
    val instagramUsername: Any,
    @SerializedName("is_subject_to_whitelist")
    val isSubjectToWhitelist: Boolean,
    @SerializedName("large_image_url")
    val largeImageUrl: String,
    @SerializedName("medium_username")
    val mediumUsername: Any,
    @SerializedName("name")
    val name: String,
    @SerializedName("only_proxied_transfers")
    val onlyProxiedTransfers: Boolean,
    @SerializedName("opensea_buyer_fee_basis_points")
    val openseaBuyerFeeBasisPoints: String,
    @SerializedName("opensea_seller_fee_basis_points")
    val openseaSellerFeeBasisPoints: String,
    @SerializedName("payout_address")
    val payoutAddress: String,
    @SerializedName("require_email")
    val requireEmail: Boolean,
    @SerializedName("safelist_request_status")
    val safelistRequestStatus: String,
    @SerializedName("short_description")
    val shortDescription: Any,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("telegram_url")
    val telegramUrl: Any,
    @SerializedName("twitter_username")
    val twitterUsername: Any,
    @SerializedName("wiki_url")
    val wikiUrl: Any
)