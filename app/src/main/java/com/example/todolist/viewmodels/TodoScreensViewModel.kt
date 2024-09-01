package com.example.todolist.viewmodels

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.TodoRepository
import com.example.todolist.data.TodoSaved
import com.example.todolist.data.models.TodoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoScreensViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
) : ViewModel() {

    private val _todosList: MutableStateFlow<List<TodoItem>> = MutableStateFlow(listOf())

    private val _isTodoSaved: MutableStateFlow<TodoSaved> = MutableStateFlow(TodoSaved.INITIAL)
    val isTodoSaved: StateFlow<TodoSaved> get() = _isTodoSaved.asStateFlow()

    private val _searchTodoText = MutableStateFlow("")
    val searchTodoText get() = _searchTodoText.asStateFlow()

    private val _isSearchingTodo: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSearchingTodo: StateFlow<Boolean> get() = _isSearchingTodo.asStateFlow()

    @OptIn(FlowPreview::class)
    // Use this state Flow if you want to search todos from the list of already retrieved todos
    val searchedTodosLocally = searchTodoText
        .onEach { _isSearchingTodo.update { true } }
        .debounce { text: String ->
            if (text.isBlank()) 0 else 2000L
        }
        .combine(_todosList) { text, todos ->
            if (text.isBlank()) {
                todos
            } else {
                todos.filter { todo ->
                    todo.title.contains(text, ignoreCase = true)
                }
            }
        }.catch { e: Throwable ->
            Log.e(TodoScreensViewModel::class.simpleName, "Exception ${e.localizedMessage}")
        }.onEach {
            _isSearchingTodo.update { false }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(4000),
            _todosList.value
        )


    @OptIn(FlowPreview::class)
    // Use this to search todos directly from the database and retrieve results
    val searchedTodosFromDb = searchTodoText
        .onEach { _isSearchingTodo.update { true } }
        .debounce { text: String ->
            if (text.isBlank()) 0 else 2000L
        }
        .map { text: String ->
            todoRepository.getTodosByTitle(title = text)
        }.catch { e: Throwable ->
            Log.e(TodoScreensViewModel::class.simpleName, "Exception ${e.localizedMessage}")
        }
        .onEach {
            _isSearchingTodo.update { false }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(4000),
            _todosList.value
        )

    init {
        viewModelScope.launch {
            // Add some initialization code
            getAllTodos()
        }
    }

    @VisibleForTesting
    suspend fun getAllTodos() {
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

    fun resetIsTodoSaved() {
        updateIsTodoSaved(todoSaved = TodoSaved.INITIAL)
    }

    fun onSearchTextChange(text: String) {
        _searchTodoText.value = text
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