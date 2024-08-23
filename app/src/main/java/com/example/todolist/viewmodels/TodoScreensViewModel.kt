package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.TodoRepository
import com.example.todolist.data.TodoSaved
import com.example.todolist.data.models.TodoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoScreensViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
) : ViewModel() {

    private var _todosList: MutableStateFlow<List<TodoItem>> = MutableStateFlow(listOf())
    val todosList: StateFlow<List<TodoItem>> get() = _todosList.asStateFlow()

    private var _isTodoSaved: MutableStateFlow<TodoSaved> = MutableStateFlow(TodoSaved.INITIAL)
    val isTodoSaved: StateFlow<TodoSaved> get() = _isTodoSaved.asStateFlow()

    init {
        viewModelScope.launch {
            // Add some initialization code
            getAllTodos()
        }
    }

    private suspend fun getAllTodos() {
        viewModelScope.launch {
            todoRepository.getAllTodos().collectLatest {
                _todosList.emit(it)
            }
        }
    }

    fun updateIsTodoSaved(todoSaved: TodoSaved = TodoSaved.SAVED) {
        viewModelScope.launch {
            _isTodoSaved.emit(todoSaved)
        }
    }

    fun insertTodo(item: TodoItem) {
        viewModelScope.launch {
            _isTodoSaved.emit(TodoSaved.SAVING)
            todoRepository.insertTodo(todoItem = item)

            delay(3000)
            _isTodoSaved.emit(TodoSaved.SAVED)
        }
    }
}