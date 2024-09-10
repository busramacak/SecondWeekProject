package com.bmprj.secondweekproject.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bmprj.secondweekproject.model.Word
import com.bmprj.secondweekproject.repository.DbRepository
import com.bmprj.secondweekproject.repository.JsonRepository
import com.bmprj.secondweekproject.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val jsonRepository: JsonRepository,
    private val dbRepository: DbRepository
) : ViewModel(){

//    private val jsonData = MutableStateFlow<List<Word>>(listOf())
    private val insert = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val _insert = insert.asStateFlow()

    private fun insertAllWords(wordList:List<Word>) = viewModelScope.launch {
       dbRepository.insertAllWord(wordList)
           .onStart {
               insert.emit(UiState.Loading)
           }.catch {
               insert.emit(UiState.Error(it))
           }.collect{
               insert.emit(UiState.Success(it))
           }
    }

    fun getJson() = viewModelScope.launch{
        jsonRepository.getWordsFromJson().collect{
//            jsonData.emit(it)
            insertAllWords(it)
        }
    }
}