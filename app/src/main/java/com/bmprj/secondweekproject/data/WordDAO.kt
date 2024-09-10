package com.bmprj.secondweekproject.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bmprj.secondweekproject.model.Word

@Dao
interface WordDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllWords(wordList:List<Word>)

    @Query("SELECT * FROM word")
    suspend fun getAllWords():List<Word>

    @Query("SELECT * FROM word WHERE isLearned=1")
    suspend fun getAllLearnedWords():List<Word>
}