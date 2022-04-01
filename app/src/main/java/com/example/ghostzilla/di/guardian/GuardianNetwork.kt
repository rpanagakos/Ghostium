package com.example.ghostzilla.di.guardian

import com.example.ghostzilla.di.common.TypeEnum
import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class GuardianNetwork(val value: TypeEnum)
