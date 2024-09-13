package com.bmprj.secondweekproject.ui.learnedList

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
class LearnedWordsViewModel @Inject constructor(
    private val dbRepository: DbRepository
) : BaseViewModel() {

    private val learnedWords = MutableStateFlow<UiState<List<Word>>>(UiState.Loading)
    val _learnedWords = learnedWords.asStateFlow()

    fun getAllLearnedWords() = viewModelScope.launch {
        dbRepository.getAllLearnedWords().emit(learnedWords)
    }
}