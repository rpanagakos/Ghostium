package com.example.ghostzilla.di.opensea

import com.example.ghostzilla.di.common.TypeEnum
import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class OpenSeaNetwork(val value: TypeEnum)