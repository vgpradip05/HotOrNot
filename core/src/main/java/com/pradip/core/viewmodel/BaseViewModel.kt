package com.pradip.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pradip.core.dispatchers.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Base abstractions for all ViewModels.
 */
abstract class BaseViewModel(private val dispatcher: Dispatcher) : ViewModel() {

    fun launchOnMain(coroutine: suspend CoroutineScope.() -> Unit) =
        launchOnViewModelScope(dispatcher.main, coroutine)

    fun launchOnBack(coroutine: suspend CoroutineScope.() -> Unit) =
        launchOnViewModelScope(dispatcher.io, coroutine)

    fun launchOnComputation(coroutine: suspend CoroutineScope.() -> Unit) =
        launchOnViewModelScope(dispatcher.computation, coroutine)

    fun launchOnDb(coroutine: suspend CoroutineScope.() -> Unit) =
        launchOnViewModelScope(dispatcher.db, coroutine)
    fun <T: BaseUiModel> emitUiState(uiModelLiveData: MutableLiveData<T>, baseUiModel: T) {
        launchOnMain {
            uiModelLiveData.postValue(baseUiModel)
        }
    }


    private fun launchOnViewModelScope(
        coroutineDispatcher: CoroutineDispatcher,
        coroutine: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(coroutineDispatcher, block = coroutine)
}