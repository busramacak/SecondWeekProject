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

    @Query("SELECT * FROM word WHERE isLearned=0")
    suspend fun getAllWords():List<Word>

    @Query("SELECT * FROM word WHERE isLearned=1")
    suspend fun getAllLearnedWords():List<Word>

    @Query("SELECT * FROM word WHERE id=:id")
    suspend fun getDetail(id:Int):Word

    @Query("UPDATE word SET isLearned=:isLerned WHERE id = :id")
    suspend fun setLearned(id:Int, isLerned:Int)

    @Query("SELECT * FROM word WHERE word LIKE '%' || :query || '%'")
    suspend fun getWord(query:String): List<Word>
}