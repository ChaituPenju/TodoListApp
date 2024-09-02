package com.example.todo.domain.usecases

import com.example.todo.domain.models.TodoItem
import com.example.todo.domain.repository.TodoRepository
import javax.inject.Inject

class InsertOneTodo @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(todoItem: TodoItem) = todoRepository.insertTodo(todoItem = todoItem)
}