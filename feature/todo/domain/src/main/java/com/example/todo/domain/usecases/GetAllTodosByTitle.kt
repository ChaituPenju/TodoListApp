package com.example.todo.domain.usecases

import com.example.todo.domain.repository.TodoRepository
import javax.inject.Inject

class GetAllTodosByTitle @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(title: String) = todoRepository.getTodosByTitle(title = title)
}