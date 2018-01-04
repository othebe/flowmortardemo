package com.othebe.flowmortardemo.common

import javax.inject.Qualifier
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ScreenScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ControlScope

@Qualifier
annotation class ApplicationContext

@Qualifier
annotation class ActivityContext