package com.example.todo.ui.viewmodels

import com.example.common.data.TodoSaved
import com.example.todo.domain.models.TodoItem
import com.example.todo.domain.usecases.GetAllTodos
import com.example.todo.domain.usecases.GetAllTodosByTitle
import com.example.todo.domain.usecases.InsertOneTodo
import com.example.todo.ui.MainTestDispatcherRule
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
@FlowPreview
class TodoScreensViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainTestDispatcherRule()

    private val mockGetAllTodos: GetAllTodos = mock()
    private val mockGetAllTodosByTitle: GetAllTodosByTitle = mock()
    private val mockInsertOneTodo: InsertOneTodo = mock()

    private lateinit var viewModel: TodoScreensViewModel

    @Before
    fun setUp() {
        Mockito.`when`(mockGetAllTodos()).thenReturn(flow<List<TodoItem>> {
            listOf(TodoItem(id = 0, title = "Test Todo"))
        })

        viewModel = TodoScreensViewModel(
            getAllTodos = mockGetAllTodos,
            getAllTodosByTitle = mockGetAllTodosByTitle,
            insertOneTodo = mockInsertOneTodo,
        )
    }

    @Test
    fun `test initial state`() {
        assertEquals(TodoSaved.INITIAL, viewModel.isTodoSaved.value)
        assertTrue(viewModel.searchTodoText.value.isEmpty())
        assertTrue(!viewModel.isSearchingTodo.value)
    }

    @Test
    fun `test updateIsTodoSaved`() = runTest {
        viewModel.updateIsTodoSaved(TodoSaved.SAVED)
        assertEquals(TodoSaved.SAVED, viewModel.isTodoSaved.value)
    }

    @Test
    fun `test resetIsTodoSaved`() = runTest {
        viewModel.updateIsTodoSaved(TodoSaved.SAVED)
        viewModel.resetIsTodoSaved()
        assertEquals(TodoSaved.INITIAL, viewModel.isTodoSaved.value)
    }

    @Test
    fun `test onSearchTextChange`() {
        viewModel.onSearchTextChange("new ")
        viewModel.onSearchTextChange("new text")
        assertEquals("new text", viewModel.searchTodoText.value)
    }

    @Test
    fun `test insertTodo`() = runTest {
        val todoItem = TodoItem(id = 1, title = "Sample Todo")

        Mockito.`when`(mockInsertOneTodo(todoItem)).thenReturn(Unit)

        viewModel.insertTodo(todoItem)

        Mockito.verify(mockInsertOneTodo).invoke(todoItem)

        assertEquals(TodoSaved.SAVING, viewModel.isTodoSaved.value)

        advanceTimeBy(3100)

        assertEquals(TodoSaved.SAVED, viewModel.isTodoSaved.value)
    }

    @Test
    fun `test searchedTodosLocally`() = runTest {
        val todos = listOf(
            TodoItem(id = 1, title = "Todo 1"),
            TodoItem(id = 2, title = "Todo 2")
        )

        Mockito.`when`(mockGetAllTodos()).thenReturn(
            flowOf(todos)
        )

        viewModel.getAllTodos()

        val job = launch {
            viewModel.searchedTodosLocally.collect()
        }

        viewModel.onSearchTextChange("1")

        assertEquals(emptyList<TodoItem>(), viewModel.searchedTodosLocally.first())
        advanceTimeBy(2100L)

        val todosResult: List<TodoItem> = viewModel.searchedTodosLocally.value

        assertEquals(1, todosResult.size)
        assertEquals("Todo 1", todosResult[0].title)
        assertNotEquals("Todo 2", todosResult[0].title)

        job.cancel()
    }

    @Test
    fun `test searchedTodosFromDb`() = runTest {
        val todos = listOf(
            TodoItem(id = 1, title = "Todo 1"),
            TodoItem(id = 2, title = "Todo 2")
        )
        val searchText = "2"

        Mockito.`when`(mockGetAllTodosByTitle(title = searchText)).thenReturn(
            listOf(todos.last())
        )

        val job = launch {
            viewModel.searchedTodosFromDb.collect()
        }

        viewModel.onSearchTextChange(text = searchText)

        assertEquals(emptyList<TodoItem>(), viewModel.searchedTodosFromDb.first())
        advanceTimeBy(2100L)

        val todosResult: List<TodoItem> = viewModel.searchedTodosFromDb.value

        assertEquals(1, todosResult.size)
        assertEquals("Todo 2", todosResult[0].title)
        assertNotEquals("Todo 1", todosResult[0].title)

        job.cancel()
    }
}
