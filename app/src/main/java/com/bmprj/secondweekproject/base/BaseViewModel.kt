package com.bmprj.secondweekproject.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bmprj.secondweekproject.util.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    @JvmName("T")
    fun <T> Flow<T>.emit(
        state:MutableStateFlow<UiState<T>>
    ){
        viewModelScope.launch {
            this@emit
                .onStart {
                    state.emit(UiState.Loading)
                }
                .catch {
                    state.emit(UiState.Error(it))
                }
                .collect{
                    state.emit(UiState.Success(it))
                }
        }
    }

    @JvmName("UiStateT")
    fun <T> Flow<UiState<T>>.emit(
        state:MutableStateFlow<UiState<T>>
    ){
        viewModelScope.launch {
            this@emit
                .onStart {
                    state.emit(UiState.Loading)
                }
                .catch {
                    state.emit(UiState.Error(it))
                }
                .collect{
                    state.emit(it)
                }
        }
    }
}