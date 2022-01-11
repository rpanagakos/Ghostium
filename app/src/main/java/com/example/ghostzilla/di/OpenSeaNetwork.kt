package com.example.ghostzilla.di

import java.lang.annotation.Documented
import javax.inject.Qualifier

@Qualifier
@Documented
@Retention(AnnotationRetention.RUNTIME)
annotation class OpenSeaNetwork(val value: TypeEnum)