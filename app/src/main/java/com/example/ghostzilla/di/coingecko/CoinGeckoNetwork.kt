package com.example.ghostzilla.di.coingecko

import com.example.ghostzilla.di.common.TypeEnum
import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class CoinGeckoNetwork(val value: TypeEnum)