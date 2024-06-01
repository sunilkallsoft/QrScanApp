package com.allsoft.qrscanapp.data.di

import com.allsoft.qrscanapp.utils.AppPreferences
import org.koin.dsl.module


val persistenceModule = module {
    /**
     * Singleton for shared preference
     **/
    single { AppPreferences(get()) }



}