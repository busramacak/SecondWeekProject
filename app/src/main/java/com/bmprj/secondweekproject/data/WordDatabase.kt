package com.bmprj.secondweekproject.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bmprj.secondweekproject.model.Word

@Database(entities= [Word::class], version = 9)
abstract class WordDatabase : RoomDatabase(){
    abstract fun wordDAO():WordDAO
}