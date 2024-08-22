package com.example.todolist

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.composables.TodoListItem
import com.example.todolist.models.TodoItem
import com.example.todolist.screens.AddTodoScreen
import com.example.todolist.screens.TodoListScreen
import com.example.todolist.ui.theme.TodoListTheme
import kotlinx.serialization.Serializable


class MainActivity : FragmentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            val navController = rememberNavController()

            TodoListTheme {
                NavHost(navController = navController, startDestination = TodoListScreen) {
                    composable<TodoListScreen> {
                        TodoListScreen(
                            onNavigateToAddTodo = {
                                navController.navigate(route = AddTodoScreen)
                            }
                        )
                    }
                    composable<AddTodoScreen> {
                        AddTodoScreen(onNavigateUp = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}

@Serializable
object TodoListScreen

@Serializable
object AddTodoScreen
