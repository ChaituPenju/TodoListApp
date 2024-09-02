package com.example.todo.domain.usecases

import com.example.todo.domain.models.TodoItem
import com.example.todo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTodos @Inject constructor(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(): Flow<List<TodoItem>> = todoRepository.getAllTodos()
}