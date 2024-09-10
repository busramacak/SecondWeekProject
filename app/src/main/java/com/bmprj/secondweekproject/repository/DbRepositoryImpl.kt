package com.bmprj.secondweekproject.repository

import com.bmprj.secondweekproject.data.WordDAO
import com.bmprj.secondweekproject.model.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DbRepositoryImpl @Inject constructor(
    private val wordDao:WordDAO
) : DbRepository {
    override suspend fun insertAllWord(wordList: List<Word>): Flow<Unit> = flow {
        emit(wordDao.insertAllWords(wordList))
    }


    override suspend fun getAllWord(): Flow<List<Word>> = flow{
        emit(wordDao.getAllWords())
    }

    override suspend fun getAllLearnedWords(): Flow<List<Word>> = flow {
        emit(wordDao.getAllLearnedWords())
    }
}