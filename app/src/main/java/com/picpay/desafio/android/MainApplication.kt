package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.di.databaseModule
import com.picpay.desafio.android.di.networkModule
import com.picpay.desafio.android.di.repositoryModule
import com.picpay.desafio.android.di.useCaseModule
import com.picpay.desafio.android.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                networkModule,
                databaseModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
                )
        }
    }
}
