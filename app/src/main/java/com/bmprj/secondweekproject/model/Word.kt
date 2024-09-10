package com.bmprj.secondweekproject.model

import com.bmprj.secondweekproject.util.Difficulty

data class Word(
    val id:Int = 0,
    val word:String,
    val translate:String,
    val pronounce:String,
    val difficulty: Difficulty,
    val imgResId:Int?
)
