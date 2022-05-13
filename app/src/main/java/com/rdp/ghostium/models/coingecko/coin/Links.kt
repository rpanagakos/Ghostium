package com.rdp.ghostium.models.coingecko.coin


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("homepage")
    val homepage: List<String>,
    @SerializedName("official_forum_url")
    val officialForumUrl: List<String>,
    @SerializedName("subreddit_url")
    val subredditUrl: String,
    @SerializedName("twitter_screen_name")
    private val twitter_screen_name : String
){
    val twitter :String?
        get() =
            if (twitter_screen_name.isEmpty())
                null
            else
                "https://twitter.com/$twitter_screen_name"

}