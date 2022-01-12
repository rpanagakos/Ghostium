package com.example.ghostzilla.models.opensea


import com.example.ghostzilla.abstraction.LocalModel
import com.google.gson.annotations.SerializedName

data class Asset(
    @SerializedName("animation_original_url")
    val animationOriginalUrl: Any,
    @SerializedName("animation_url")
    val animationUrl: Any,
    @SerializedName("asset_contract")
    val assetContract: AssetContract,
    @SerializedName("background_color")
    val backgroundColor: Any,
    @SerializedName("collection")
    val collection: Collection,
    @SerializedName("creator")
    val creator: Creator,
    @SerializedName("decimals")
    val decimals: Any,
    @SerializedName("description")
    val description: Any,
    @SerializedName("external_link")
    val externalLink: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_original_url")
    val imageOriginalUrl: String?,
    @SerializedName("image_preview_url")
    val imagePreviewUrl: String?,
    @SerializedName("image_thumbnail_url")
    val imageThumbnailUrl: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("is_presale")
    val isPresale: Boolean,
    @SerializedName("last_sale")
    val lastSale: Any,
    @SerializedName("listing_date")
    val listingDate: Any,
    @SerializedName("name")
    val name: String?,
    @SerializedName("num_sales")
    val numSales: Int,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("permalink")
    val permalink: String,
    @SerializedName("sell_orders")
    val sellOrders: Any,
    @SerializedName("token_id")
    val tokenId: String,
    @SerializedName("token_metadata")
    val tokenMetadata: Any,
    @SerializedName("top_bid")
    val topBid: Any,
    @SerializedName("traits")
    val traits: List<Any>,
    @SerializedName("transfer_fee")
    val transferFee: Any,
    @SerializedName("transfer_fee_payment_token")
    val transferFeePaymentToken: Any
) : LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}