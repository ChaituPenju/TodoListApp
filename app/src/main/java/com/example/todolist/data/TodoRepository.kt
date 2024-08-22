package com.example.todolist.data

import com.example.todolist.data.database.TodoDao
import com.example.todolist.data.models.TodoItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TodoRepository @Inject constructor(
    private val todoDao: TodoDao,
) {

    fun getAllTodos(): Flow<List<TodoItem>> {
        return todoDao.getAllTodos()
    }

    suspend fun insertTodo(todoItem: TodoItem) {
        todoDao.insertTodo(item = todoItem)
    }

    suspend fun updateTodo(todoItem: TodoItem) {
        todoDao.updateTodo(item = todoItem)
    }

}