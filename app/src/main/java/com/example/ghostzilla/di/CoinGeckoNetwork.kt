package com.example.ghostzilla.di

import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class CoinGeckoNetwork(val value: TypeEnum)