package com.posse.android.moneyconverter.view.main.viewModel

import com.posse.android.moneyconverter.di.annotations.Interactor
import com.posse.android.moneyconverter.model.AppState
import com.posse.android.moneyconverter.model.DataSource
import com.posse.android.moneyconverter.model.data.Response
import com.posse.android.moneyconverter.view.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(
    @Interactor private val interactor: DataSource<Response>
) : BaseViewModel<AppState<*>>() {

    fun getData(isOnline: Boolean) {
        stateLiveData.value = AppState.Loading
        coroutineScope.launch {
            try {
                val result = interactor.getData(isOnline)
                stateLiveData.postValue(AppState.Success(result))
            } catch (e: Exception) {
                stateLiveData.postValue(AppState.Error(e))
            }
        }
    }
}