package com.rdp.ghostium.di.guardian

import com.rdp.ghostium.di.common.TypeEnum
import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class GuardianNetwork(val value: TypeEnum)
