package com.allsoft.qrscanapp.data.di

import com.allsoft.qrscanapp.data.viewModel.LoginViewModel
import com.allsoft.qrscanapp.data.viewModel.MasterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MasterViewModel(get()) }
    viewModel { LoginViewModel(get()) }

}