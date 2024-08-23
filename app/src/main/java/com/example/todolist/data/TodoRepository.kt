package com.example.todolist.data

import com.example.todolist.data.database.TodoDao
import com.example.todolist.data.models.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject


class TodoRepository @Inject constructor(
    private val todoDao: TodoDao,
) {

    fun getAllTodos(): Flow<List<TodoItem>> {
        return todoDao.getAllTodos()
    }

    suspend fun getTodosByTitle(title: String): List<TodoItem> {
       return todoDao.getTodosByTitle(itemTitle = "%$title%")
    }

    suspend fun insertTodo(todoItem: TodoItem) {
        todoDao.insertTodo(item = todoItem)
    }

    suspend fun updateTodo(todoItem: TodoItem) {
        todoDao.updateTodo(item = todoItem)
    }

}