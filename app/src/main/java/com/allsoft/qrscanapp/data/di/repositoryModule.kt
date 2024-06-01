package com.allsoft.qrscanapp.data.di

import com.allsoft.qrscanapp.data.source.LoginRepo
import com.allsoft.qrscanapp.data.source.MasterRepo
import org.koin.dsl.module

/**
 * Use the [repositoryModule] to creating repository instance
 **/
val repositoryModule = module {
    single { MasterRepo(get(), get()) }
    single { LoginRepo(get(), get()) }
}