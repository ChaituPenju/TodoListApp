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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todolist.composables.TodoAppBar
import com.example.todolist.data.TodoSaved
import com.example.todolist.data.models.TodoItem
import com.example.todolist.data.todoErrorEntry
import com.example.todolist.viewmodels.TodoScreensViewModel

@Composable
fun AddTodoScreen(viewModel: TodoScreensViewModel, onNavigateUp: () -> Unit) {
    val isTodoSaved by viewModel.isTodoSaved.collectAsState()

    var title by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            TodoAppBar(
                title = "Add Todo Item",
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
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                enabled = title.isNotBlank(),
                onClick = {
                    try {
                        handleSaveTodo(title = title) {
                            viewModel.insertTodo(TodoItem(title = title))
                        }
                    } catch (e: Exception) {
                        Log.e("TodoActivity::AddTodoScreen", "Exception : ${e.localizedMessage}")

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
                        onNavigateUp()
                        Text("Add TODO")
                    }
                    else -> Text("Add TODO")
                }
            }
        }
    }
}

fun handleSaveTodo(title: String, onSaveTodo: () -> Unit) {
    if (title == todoErrorEntry) {
        throw IllegalArgumentException("Failed to add TODO")
    } else {
        onSaveTodo.invoke()
    }
}
