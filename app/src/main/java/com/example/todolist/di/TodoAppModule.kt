package com.example.todolist.di

import android.content.Context
import com.example.todolist.database.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TodoAppModule {

    @Provides
    fun providesDatabase(@ApplicationContext applicationContext: Context): TodoDatabase {
        return TodoDatabase.getInstance(context = applicationContext)
    }

    @Provides
    fun providesDao(database: TodoDatabase): com.example.todo.data.local.TodoDao {
        return database.todoDao()
    }
}