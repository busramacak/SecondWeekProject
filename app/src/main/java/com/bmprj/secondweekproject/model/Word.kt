package com.bmprj.secondweekproject.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.bmprj.secondweekproject.util.Difficulty

@Entity(tableName = "word", indices = [Index(value = ["word"], unique = true)])
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val word:String,
    val translateTr:String,
    val translateFr:String,
    val pronounce:String,
    val pronounceFr:String,
    val difficulty: Difficulty,
    val imgResId:String,
    val isLearned:Boolean=false,
    val sentence:String,
    val sentenceTr:String,
    val sentenceFr:String
)
