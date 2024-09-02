package com.example.todo.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.data.TodoSaved
import com.example.common.data.todoErrorEntry
import com.example.todo.ui.R
import com.example.todo.ui.composables.TodoAppBar
import com.example.todo.ui.navigation.AddTodoScreen
import com.example.todo.ui.viewmodels.TodoScreensViewModel

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
                .fillMaxSize()
                .padding(top = 44.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            TextFieldTodo(
                value = todoTitle,
                onValueChange = { todoTitle = it },
                labelText = stringResource(id = R.string.todo_text_field_label_title),
            )
            Spacer(modifier = Modifier.height(24.dp))
            ButtonAddTodo(
                modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                todoTitle = todoTitle,
                onClick = {
                    try {
                        handleSaveTodo(title = todoTitle) {
                            viewModel.insertTodo(com.example.todo.domain.models.TodoItem(title = todoTitle))
                        }
                    } catch (e: Exception) {
                        Log.e(AddTodoScreen.javaClass.simpleName, "Exception : ${e.localizedMessage}")

                        viewModel.updateIsTodoSaved(todoSaved = TodoSaved.ERROR)
                        onNavigateUp()
                    }
                },
                onTodoSaved = {
                    viewModel.resetIsTodoSaved()
                    onNavigateUp()
                },
                isTodoSaved = isTodoSaved,
            )
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



/**
 *
 * Composable Functions
 *
 */
@Composable
fun ButtonAddTodo(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    todoTitle: String,
    onTodoSaved: () -> Unit,
    isTodoSaved: TodoSaved,
) {
    Button(
        modifier = modifier,
        enabled = todoTitle.isNotBlank(),
        onClick = onClick,
    ) {
        when (isTodoSaved) {
            TodoSaved.SAVING -> CircularProgressIndicator(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                color = Color.Green,
            )
            TodoSaved.SAVED -> {
                onTodoSaved()

                Text(text = stringResource(R.string.add_todo_button_title))
            }
            else -> Text(text = stringResource(R.string.add_todo_button_title))
        }
    }
}

@Composable
fun TextFieldTodo(
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        label = { Text(text = labelText) },
    )
}