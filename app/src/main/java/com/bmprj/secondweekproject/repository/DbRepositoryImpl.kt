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

    override suspend fun getDetail(id: Int): Flow<Word> = flow {
        emit(wordDao.getDetail(id))
    }

    override suspend fun setLearned(id: Int, isLearned:Int): Flow<Unit> = flow {
        emit(wordDao.setLearned(id,isLearned))
    }
}