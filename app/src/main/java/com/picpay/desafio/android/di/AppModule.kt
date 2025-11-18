package com.picpay.desafio.android.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.data.local.UserDatabase
import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.data.mapper.UserDtoMapperData
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.ui.user.mapper.UserDtoMapperUI
import com.picpay.desafio.android.ui.user.viewModel.UserViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    val baseUrl = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

    single {
        OkHttpClient.Builder().build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    single<PicPayService> {
        get<Retrofit>().create(PicPayService::class.java)
    }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            UserDatabase::class.java,
            "users_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single<UserDao> {
        val database = get<UserDatabase>()
        database.userDao()
    }
}

val repositoryModule = module {
    single { UserDtoMapperData() }
    single<UserRepository> { UserRepositoryImpl(
        userMapper = get(),
        apiService = get(),
        userDao = get()
    ) }
}

val useCaseModule = module {
    factory { GetUsersUseCase(userRepository = get()) }
}

val viewModelModule =  module {
    single { UserDtoMapperUI() }
    viewModel { UserViewModel(useCase = get(), mapperUI = get()) }
}
