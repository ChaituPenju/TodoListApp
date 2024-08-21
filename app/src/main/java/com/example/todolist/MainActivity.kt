package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todolist.composables.TodoListItem
import com.example.todolist.ui.theme.TodoListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TodoListTheme {
                Scaffold(
                    modifier = Modifier.windowInsetsPadding(WindowInsets.safeContent)
                ) { innerPadding ->
                    Text(
                        modifier = Modifier.padding(paddingValues = innerPadding),
                        text = "hello world"
                    )
                }
            }
        }
    }
}

@Composable
fun TodoListItems() {
    LazyColumn {
        items(count = 10) {
            TodoListItem()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainActivityPreview() {
    TodoListTheme {
        TodoListItems()
    }
}