package com.bmprj.secondweekproject.ui.wordList

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
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordListViewModel @Inject constructor(
    private val dbRepository: DbRepository,
) : ViewModel() {

    private val words = MutableStateFlow<UiState<List<Word>>>(UiState.Loading)
    val _words = words.asStateFlow()

    private val filteredCoins = MutableStateFlow<List<Word>>(listOf())
    val _filteredCoins = filteredCoins.asStateFlow()

    private val isNewWordAdd = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val _isNewWordAdd  = isNewWordAdd.asStateFlow()


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
            filteredCoins.emit(it)
        }

    }

    fun addNewWord(wordModel: Word) = viewModelScope.launch{
        dbRepository.addNewWord(wordModel)
            .onStart {
                isNewWordAdd.emit(UiState.Loading)
            }
            .catch {
                isNewWordAdd.emit(UiState.Error(it))
            }
            .collect{
                isNewWordAdd.emit(UiState.Success(it))
            }
    }


}