package com.picpay.desafio.android.testUtils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.domain.model.UserDomainModel
import com.picpay.desafio.android.ui.ViewState
import com.picpay.desafio.android.ui.user.model.UserUIModel

class FakeViewModel : ViewModel() {

    private val _state = MutableLiveData<ViewState<List<UserUIModel>>>()
    val state: LiveData<ViewState<List<UserUIModel>>> get() = _state

    fun postStateValue(value: ViewState<List<UserUIModel>>) {
        _state.postValue(value)
    }
}
