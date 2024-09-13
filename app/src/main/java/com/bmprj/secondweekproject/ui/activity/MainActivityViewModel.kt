package com.bmprj.secondweekproject.ui.activity


import androidx.lifecycle.viewModelScope
import com.bmprj.secondweekproject.base.BaseViewModel
import com.bmprj.secondweekproject.model.Word
import com.bmprj.secondweekproject.repository.DbRepository
import com.bmprj.secondweekproject.repository.JsonRepository
import com.bmprj.secondweekproject.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val jsonRepository: JsonRepository,
    private val dbRepository: DbRepository,
) : BaseViewModel() {

    private val insert = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val _insert = insert.asStateFlow()

    private fun insertAllWords(wordList: List<Word>) = viewModelScope.launch {
        dbRepository.insertAllWord(wordList).emit(insert)
    }

    fun getJson() = viewModelScope.launch {
        jsonRepository.getWordsFromJson().collect {
            insertAllWords(it)
        }
    }
}