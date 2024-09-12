package com.bmprj.secondweekproject.ui.wordList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
) : ViewModel() {

    private val words = MutableStateFlow<UiState<List<Word>>>(UiState.Loading)
    val _words = words.asStateFlow()
    private val _filteredCoins = MutableStateFlow<List<Word>>(listOf())
    val filteredCoins = _filteredCoins.asStateFlow()


    fun gelAllWords() = viewModelScope.launch {
        dbRepository.getAllWord().collect { wordList ->
            val filteredWords = wordList.filter { !it.isLearned }
            words.emit(UiState.Success(filteredWords))
        }
    }

    fun refreshData() = viewModelScope.launch {
        dbRepository.getAllWord().collect { wordList ->
            val filteredWords = wordList.filter { !it.isLearned }
            val mix = filteredWords.shuffled()
            words.emit(UiState.Success(mix))
        }
    }

    fun getDataForQuery(query: String) = viewModelScope.launch {
        dbRepository.getWord(query).collect{
            _filteredCoins.emit(it)
        }

    }

}