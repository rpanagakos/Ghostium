package com.example.ghostzilla.models.coingecko.coin


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("homepage")
    val homepage: List<String>,
    @SerializedName("official_forum_url")
    val officialForumUrl: List<String>,
    @SerializedName("subreddit_url")
    val subredditUrl: String
)