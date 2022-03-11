package com.posse.android.moneyconverter.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.posse.android.moneyconverter.model.AppState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

abstract class BaseViewModel<T : AppState<*>> : ViewModel() {

    protected val stateLiveData: MutableLiveData<T> = MutableLiveData()

    protected var isOnline = false

    protected val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun getStateLiveData(): LiveData<T> = stateLiveData

    override fun onCleared() {
        coroutineScope.coroutineContext.cancelChildren()
    }
}