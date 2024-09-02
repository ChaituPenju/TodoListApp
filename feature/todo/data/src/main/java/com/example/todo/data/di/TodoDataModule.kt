package com.example.todo.data.di

import com.example.todo.data.local.TodoDao
import com.example.todo.data.repository.TodoRepositoryImpl
import com.example.todo.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object SearchDataModule {

    @Provides
    fun providesTodoRepository(todoDao: TodoDao): TodoRepository {
        return TodoRepositoryImpl(todoDao = todoDao)
    }
}
