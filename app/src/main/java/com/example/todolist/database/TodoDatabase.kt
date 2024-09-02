package com.example.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [com.example.todo.domain.models.TodoItem::class])
abstract class TodoDatabase: RoomDatabase() {

    abstract fun todoDao(): com.example.todo.data.local.TodoDao

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