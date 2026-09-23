package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.ChatMessage
import com.example.data.model.CollabGig
import com.example.data.model.CollabRequest
import com.example.data.model.CreativePost
import com.example.data.model.CreativeUser

@Database(
    entities = [
        CreativePost::class,
        CreativeUser::class,
        ChatMessage::class,
        CollabRequest::class,
        CollabGig::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FloDatabase : RoomDatabase() {
    abstract fun floDao(): FloDao

    companion object {
        @Volatile
        private var INSTANCE: FloDatabase? = null

        fun getInstance(context: Context): FloDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FloDatabase::class.java,
                    "flo_creative_database.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
