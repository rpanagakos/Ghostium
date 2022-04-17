package com.rdp.ghostium.di.opensea

import com.rdp.ghostium.di.common.TypeEnum
import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class OpenSeaNetwork(val value: TypeEnum)