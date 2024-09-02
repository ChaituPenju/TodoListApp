package com.example.todo.ui.screens

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.data.TodoSaved
import com.example.todo.ui.R
import com.example.todo.ui.composables.PopupDialog
import com.example.todo.ui.composables.TodoAppBar
import com.example.todo.ui.composables.TodoListItem
import com.example.todo.ui.viewmodels.TodoScreensViewModel

@Composable
fun TodoListScreen(viewModel: TodoScreensViewModel, onNavigateToAddTodo: () -> Unit) {
    var isPopupVisible by remember { mutableStateOf(false) }

    // Use this if you want to get all todos from database and search locally
    // val todoItemsAll by viewModel.searchedTodosLocally.collectAsState()

    val todoItems by viewModel.searchedTodosFromDb.collectAsStateWithLifecycle()
    val isTodoSaved by viewModel.isTodoSaved.collectAsStateWithLifecycle()

    val searchQuery by viewModel.searchTodoText.collectAsState()
    val isSearching by viewModel.isSearchingTodo.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val orientation = LocalConfiguration.current.orientation


    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            Column {
                TodoAppBar(
                    title = stringResource(id = R.string.todo_list_screen_appbar_title),
                    onNavigationIconClick = null,
                )

                SearchTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    value = searchQuery,
                    onValueChange = viewModel::onSearchTextChange,
                    isSearching = isSearching,
                )
            }

        },
        floatingActionButtonPosition = FabPosition.EndOverlay,
        floatingActionButton = {
            TodoFloatingActionButton(onClick = onNavigateToAddTodo)
        }
    ) { paddingValues ->
        DisposableEffect(key1 = isTodoSaved) {
            if (isTodoSaved == TodoSaved.ERROR) {
                isPopupVisible = true
            }

            onDispose {
                viewModel.resetIsTodoSaved()
            }
        }

        LaunchedEffect(key1 = isSearching) {
            if (!isSearching && orientation == ORIENTATION_LANDSCAPE) {
                keyboardController?.hide()
            }
        }

        if (todoItems.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                TextIfListEmpty(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.todos_list_empty_title),
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues = paddingValues)
                    .padding(all = 12.dp)
            ) {
                items(items = todoItems) { todoItem ->
                    TodoListItem(todoItem = todoItem)
                }
            }
        }
    }

    PopupDialog(
        isVisible = isPopupVisible,
        message = stringResource(id = R.string.failed_add_todo_error_message),
        onDismiss = {
            isPopupVisible = false
        }
    )
}



/**
 *
 * Composable functions
 *
 */

@Composable
fun TodoFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Icon")
    }
}

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isSearching: Boolean,
) {
    OutlinedTextField(
        modifier = modifier,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = stringResource(id = R.string.search_todos_placeholder_text))
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Icon")
        },
        trailingIcon = {
            if (isSearching) {
                CircularProgressIndicator()
            } else Unit
        }
    )
}

@Composable
fun TextIfListEmpty(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.secondary,
            fontStyle = FontStyle.Italic,
        )
    )
}


