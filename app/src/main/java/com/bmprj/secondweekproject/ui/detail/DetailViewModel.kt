package com.bmprj.secondweekproject.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bmprj.secondweekproject.model.Word
import com.bmprj.secondweekproject.repository.DbRepository
import com.bmprj.secondweekproject.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val dbRepository: DbRepository
) : ViewModel(){

    private val detailWord = MutableStateFlow<UiState<Word>>(UiState.Loading)
    val _detailWord = detailWord.asStateFlow()


    private val setLearned = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val _setLearned = setLearned.asStateFlow()

    fun getDetail(id:Int) = viewModelScope.launch {
        dbRepository.getDetail(id)
            .onStart {
                detailWord.emit(UiState.Loading)
            }.catch {
                detailWord.emit(UiState.Error(it))
            }.collect{
                detailWord.emit((UiState.Success(it)))
            }
    }

    fun setLearned(id:Int, isLearned:Int) = viewModelScope.launch{
        dbRepository.setLearned(id,isLearned)
            .onStart {
                setLearned.emit(UiState.Loading)
            }.catch {
                setLearned.emit(UiState.Error(it))
            }.collect{
                setLearned.emit((UiState.Success(it)))
            }
    }
}