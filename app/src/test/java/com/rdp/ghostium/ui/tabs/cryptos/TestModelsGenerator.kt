package com.rdp.ghostium.ui.tabs.cryptos

import com.rdp.ghostium.models.coingecko.search.CoinResult
import com.rdp.ghostium.models.coingecko.search.CoinsSearched


class TestModelsGenerator {
    private var coinSearched: CoinsSearched = CoinsSearched(arrayListOf())

    init {
        coinSearched = getMockData()
    }

    fun generateCoins(): CoinsSearched {
        return coinSearched
    }

    fun generateCoinsWithEmptyList(): CoinsSearched {
        return CoinsSearched(arrayListOf())
    }

    fun generateCoinItemModel(): CoinResult {
        return coinSearched.coinResults[0]
    }

    fun getStubSearchTitle(): String {
        return coinSearched.coinResults[0].name
    }

    private fun getMockData() =
        CoinsSearched(arrayListOf(CoinResult("0", "image", 10000, "name", "symbol", "thumb"),
            CoinResult("0", "image", 10000, "name", "symbol", "thumb"),
            CoinResult("0", "image", 10000, "name", "symbol", "thumb"),
            CoinResult("0", "image", 10000, "name", "symbol", "thumb")))

}
