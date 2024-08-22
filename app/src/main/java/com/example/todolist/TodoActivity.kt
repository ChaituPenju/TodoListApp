package com.example.todolist

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.screens.AddTodoScreen
import com.example.todolist.screens.TodoListScreen
import com.example.todolist.ui.theme.TodoListTheme
import com.example.todolist.viewmodels.TodoScreensViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val viewModel: TodoScreensViewModel by viewModels()

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
