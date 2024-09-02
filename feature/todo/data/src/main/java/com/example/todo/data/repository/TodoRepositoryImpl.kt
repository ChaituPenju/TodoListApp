package com.example.todo.data.repository

import com.example.todo.data.local.TodoDao
import com.example.todo.domain.models.TodoItem
import com.example.todo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow


class TodoRepositoryImpl(
    private val todoDao: TodoDao,
): TodoRepository {

    override fun getAllTodos(): Flow<List<TodoItem>> {
        return todoDao.getAllTodos()
    }

    override suspend fun getTodosByTitle(title: String): List<TodoItem> {
       return todoDao.getTodosByTitle(itemTitle = "%$title%")
    }

    override suspend fun insertTodo(todoItem: TodoItem) {
        todoDao.insertTodo(item = todoItem)
    }

    override suspend fun updateTodo(todoItem: TodoItem) {
        todoDao.updateTodo(item = todoItem)
    }
}