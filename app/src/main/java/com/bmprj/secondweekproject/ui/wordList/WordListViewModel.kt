package com.bmprj.secondweekproject.ui.wordList

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
class WordListViewModel @Inject constructor(
    private val dbRepository: DbRepository,
) : BaseViewModel() {

    private val words = MutableStateFlow<UiState<List<Word>>>(UiState.Loading)
    val _words = words.asStateFlow()

    private val filteredWords = MutableStateFlow<UiState<List<Word>>>(UiState.Loading)
    val _filteredWords = filteredWords.asStateFlow()

    private val isNewWordAdd = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val _isNewWordAdd  = isNewWordAdd.asStateFlow()


    fun gelAllWords() = viewModelScope.launch {
        dbRepository.getAllWord().emit(words)
    }

    fun refreshData() = viewModelScope.launch {
        dbRepository.getAllWord().emit(words)
    }

    fun getDataForQuery(query: String) = viewModelScope.launch {
        dbRepository.getWord(query).emit(filteredWords)
    }

    fun addNewWord(wordModel: Word) = viewModelScope.launch{
        dbRepository.addNewWord(wordModel).emit(isNewWordAdd)
    }


}