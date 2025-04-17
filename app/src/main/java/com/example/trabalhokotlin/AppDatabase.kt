package com.example.trabalhokotlin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tarefa::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tarefaDao(): TarefaDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tarefas.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
