package com.example.todolist.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todolist.AddTodoScreen
import com.example.todolist.R
import com.example.todolist.composables.TodoAppBar
import com.example.todolist.data.TodoSaved
import com.example.todolist.data.models.TodoItem
import com.example.todolist.data.todoErrorEntry
import com.example.todolist.viewmodels.TodoScreensViewModel

@Composable
fun AddTodoScreen(viewModel: TodoScreensViewModel, onNavigateUp: () -> Unit) {
    val isTodoSaved by viewModel.isTodoSaved.collectAsStateWithLifecycle()

    var todoTitle by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            TodoAppBar(
                title = stringResource(id = R.string.add_todo_screen_appbar_title),
                onNavigationIconClick = { onNavigateUp() },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            OutlinedTextField(
                value = todoTitle,
                onValueChange = { todoTitle = it },
                label = { Text(text = stringResource(id = R.string.todo_text_field_label_title)) },
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                enabled = todoTitle.isNotBlank(),
                onClick = {
                    try {
                        handleSaveTodo(title = todoTitle) {
                            viewModel.insertTodo(TodoItem(title = todoTitle))
                        }
                    } catch (e: Exception) {
                        Log.e(AddTodoScreen.javaClass.simpleName, "Exception : ${e.localizedMessage}")

                        viewModel.updateIsTodoSaved(todoSaved = TodoSaved.ERROR)
                        onNavigateUp()
                    }
                },
            ) {
                when (isTodoSaved) {
                    TodoSaved.SAVING -> CircularProgressIndicator(
                        modifier = Modifier.align(alignment = Alignment.CenterVertically),
                        color = Color.Green,
                    )
                    TodoSaved.SAVED -> {
                        viewModel.resetIsTodoSaved()
                        onNavigateUp()

                        Text(text = stringResource(R.string.add_todo_button_title))
                    }
                    else -> Text(text = stringResource(R.string.add_todo_button_title))
                }
            }
        }
    }
}

fun handleSaveTodo(title: String, onSaveTodo: () -> Unit) {
    if (title == todoErrorEntry) {
        throw IllegalArgumentException("Failed to Add TODO")
    } else {
        onSaveTodo.invoke()
    }
}
