package com.example.ghostzilla.di

import java.lang.annotation.Documented
import javax.inject.Qualifier

@Qualifier
@Documented
@Retention(AnnotationRetention.RUNTIME)
annotation class CoinGeckoNetwork(val value: TypeEnum)