package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.domain.mapper.UserDtoMapper
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.ui.user.viewModel.UserViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { UserDtoMapper() }

    single<UserRepository> { UserRepositoryImpl(userMapper = get()) }

    factory { GetUsersUseCase(userRepository = get()) }

    viewModel { UserViewModel(useCase = get()) }
}
