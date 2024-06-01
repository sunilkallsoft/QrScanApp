package com.allsoft.qrscanapp

import android.app.Application
import android.os.StrictMode
import com.allsoft.qrscanapp.data.di.apiModule
import com.allsoft.qrscanapp.data.di.persistenceModule
import com.allsoft.qrscanapp.data.di.repositoryModule
import com.allsoft.qrscanapp.data.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApp : Application() {

    companion object{
        var mContext : BaseApp?= null
    }

    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
        }

        super.onCreate()

        mContext = this

        startKoin {

            androidContext(this@BaseApp)

            modules(listOf(persistenceModule, apiModule, repositoryModule, viewModelModule))
        }
    }


}