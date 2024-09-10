package com.bmprj.secondweekproject.ui.wordList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bmprj.secondweekproject.model.Word
import com.bmprj.secondweekproject.repository.DbRepository
import com.bmprj.secondweekproject.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordListViewModel @Inject constructor(
    private val dbRepository:DbRepository
) :ViewModel(){

    private val words = MutableStateFlow<UiState<List<Word>>>(UiState.Loading)
    val _words = words.asStateFlow()

    fun insertAllWords(wordList:List<Word>) = viewModelScope.launch {
        dbRepository.insertAllWord(wordList).catch {
            println(it.message)
        }
            .collect{
                println("oldi")
            }
    }

    fun gelAllWords() = viewModelScope.launch {
        dbRepository.getAllWord().collect{
            words.emit(UiState.Success(it))
        }
    }
}