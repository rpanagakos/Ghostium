package com.example.ghostzilla.di

import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class OpenSeaNetwork(val value: TypeEnum)