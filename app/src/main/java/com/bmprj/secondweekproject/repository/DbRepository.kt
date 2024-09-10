package com.bmprj.secondweekproject.repository

import com.bmprj.secondweekproject.model.Word
import kotlinx.coroutines.flow.Flow

interface DbRepository {
    suspend fun insertAllWord(wordList: List<Word>) : Flow<Unit>
    suspend fun getAllWord():Flow<List<Word>>
    suspend fun getAllLearnedWords():Flow<List<Word>>
    suspend fun getDetail(id:Int):Flow<Word>
}