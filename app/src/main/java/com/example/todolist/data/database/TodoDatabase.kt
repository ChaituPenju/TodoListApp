package com.example.todolist.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.data.models.TodoItem

@Database(version = 1, entities = [TodoItem::class])
abstract class TodoDatabase: RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var dbInstance: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase {
            return dbInstance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "app_database"
                ).build()

                dbInstance = instance
                dbInstance!!
            }
        }
    }
}