package com.example.ghostzilla.models.opensea


import com.google.gson.annotations.SerializedName

data class AssetContract(
    @SerializedName("address")
    val address: String,
    @SerializedName("asset_contract_type")
    val assetContractType: String,
    @SerializedName("buyer_fee_basis_points")
    val buyerFeeBasisPoints: Int,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("default_to_fiat")
    val defaultToFiat: Boolean,
    @SerializedName("description")
    val description: String,
    @SerializedName("dev_buyer_fee_basis_points")
    val devBuyerFeeBasisPoints: Int,
    @SerializedName("dev_seller_fee_basis_points")
    val devSellerFeeBasisPoints: Int,
    @SerializedName("external_link")
    val externalLink: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nft_version")
    val nftVersion: String,
    @SerializedName("only_proxied_transfers")
    val onlyProxiedTransfers: Boolean,
    @SerializedName("opensea_buyer_fee_basis_points")
    val openseaBuyerFeeBasisPoints: Int,
    @SerializedName("opensea_seller_fee_basis_points")
    val openseaSellerFeeBasisPoints: Int,
    @SerializedName("opensea_version")
    val openseaVersion: Any,
    @SerializedName("owner")
    val owner: Int,
    @SerializedName("payout_address")
    val payoutAddress: String,
    @SerializedName("schema_name")
    val schemaName: String,
    @SerializedName("seller_fee_basis_points")
    val sellerFeeBasisPoints: Int,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("total_supply")
    val totalSupply: String
)