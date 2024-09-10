package com.bmprj.secondweekproject.repository

import com.bmprj.secondweekproject.data.WordDAO
import com.bmprj.secondweekproject.model.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DbRepositoryImpl(
    private val wordDao:WordDAO
) : DbRepository {
    override suspend fun insertAllWord(wordList: List<Word>): Flow<Unit> = flow {
        println(wordList)
        emit(Unit)
    }


    override suspend fun getAllWord(): Flow<List<Word>> {
        TODO("Not yet implemented")
    }
}