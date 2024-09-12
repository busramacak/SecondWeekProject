package com.bmprj.secondweekproject.repository

import com.bmprj.secondweekproject.model.Word
import kotlinx.coroutines.flow.Flow

interface DbRepository {
    suspend fun insertAllWord(wordList: List<Word>): Flow<Unit>
    suspend fun getAllWord(): Flow<List<Word>>
    suspend fun getAllLearnedWords(): Flow<List<Word>>
    suspend fun getDetail(id: Int): Flow<Word>
    suspend fun setLearned(id: Int, isLearned: Int): Flow<Unit>
    suspend fun getWord(query: String): Flow<List<Word>>
    suspend fun addNewWord(word: Word): Flow<Unit>
}