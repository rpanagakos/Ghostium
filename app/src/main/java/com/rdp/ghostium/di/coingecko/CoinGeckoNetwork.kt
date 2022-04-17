package com.rdp.ghostium.di.coingecko

import com.rdp.ghostium.di.common.TypeEnum
import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class CoinGeckoNetwork(val value: TypeEnum)