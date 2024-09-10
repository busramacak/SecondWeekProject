package com.bmprj.secondweekproject.repository

import com.bmprj.secondweekproject.model.Word
import kotlinx.coroutines.flow.Flow

interface JsonRepository {
    suspend fun getWordsFromJson(): Flow<List<Word>>
}