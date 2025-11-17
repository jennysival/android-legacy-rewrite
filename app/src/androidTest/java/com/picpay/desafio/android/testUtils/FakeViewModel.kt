package com.picpay.desafio.android.testUtils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.domain.model.UserModel
import com.picpay.desafio.android.ui.ViewState

class FakeViewModel : ViewModel() {

    private val _state = MutableLiveData<ViewState<List<UserModel>>>()
    val state: LiveData<ViewState<List<UserModel>>> get() = _state

    fun postStateValue(value: ViewState<List<UserModel>>) {
        _state.postValue(value)
    }
}
