package com.bmprj.secondweekproject.repository

import android.content.Context
import com.bmprj.secondweekproject.R
import com.bmprj.secondweekproject.model.Word
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JsonRepositoryImpl @Inject constructor(
   @ApplicationContext private val context: Context
) :JsonRepository {
    override suspend fun getWordsFromJson(): Flow<List<Word>> = flow {
        val json = context.resources.openRawResource(R.raw.data).bufferedReader().use { it.readText() }
        val gson = Gson()
        val type = object : TypeToken<List<Word>>() {}.type
        emit(gson.fromJson(json, type))
    }
}