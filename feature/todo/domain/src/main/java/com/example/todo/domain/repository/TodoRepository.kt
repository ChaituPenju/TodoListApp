package com.example.todo.domain.repository

import com.example.todo.domain.models.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getAllTodos(): Flow<List<TodoItem>>

    suspend fun getTodosByTitle(title: String): List<TodoItem>

    suspend fun insertTodo(todoItem: TodoItem)

    suspend fun updateTodo(todoItem: TodoItem)
}