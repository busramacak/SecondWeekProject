package com.bmprj.secondweekproject.ui.detail

import androidx.lifecycle.viewModelScope
import com.bmprj.secondweekproject.base.BaseViewModel
import com.bmprj.secondweekproject.model.Word
import com.bmprj.secondweekproject.repository.DbRepository
import com.bmprj.secondweekproject.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val dbRepository: DbRepository
) : BaseViewModel(){

    private val detailWord = MutableStateFlow<UiState<Word>>(UiState.Loading)
    val _detailWord = detailWord.asStateFlow()


    private val setLearned = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val _setLearned = setLearned.asStateFlow()

    fun getDetail(id:Int) = viewModelScope.launch {
        dbRepository.getDetail(id).emit(detailWord)
    }

    fun setLearned(id:Int, isLearned:Int) = viewModelScope.launch{
        dbRepository.setLearned(id,isLearned).emit(setLearned)
    }
}