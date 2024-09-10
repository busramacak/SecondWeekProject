package com.bmprj.secondweekproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bmprj.secondweekproject.util.Difficulty

@Entity(tableName = "word")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val word:String,
    val translate:String,
    val pronounce:String,
    val difficulty: Difficulty,
    val imgResId:Int?,
    val isLearned:Boolean=false
)
