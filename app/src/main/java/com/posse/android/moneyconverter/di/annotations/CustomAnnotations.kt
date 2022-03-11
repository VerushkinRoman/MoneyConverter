package com.posse.android.moneyconverter.di.annotations

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Local

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Interactor