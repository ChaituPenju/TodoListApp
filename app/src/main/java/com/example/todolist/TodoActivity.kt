package com.example.todolist

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.ScaffoldDefaults
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.screens.AddTodoScreen
import com.example.todolist.screens.TodoListScreen
import com.example.todolist.ui.theme.TodoListTheme
import com.example.todolist.viewmodels.TodoScreensViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()

            TodoListTheme {
                ScaffoldDefaults.contentWindowInsets

                val viewModel = hiltViewModel<TodoScreensViewModel>()

                NavHost(navController = navController, startDestination = TodoListScreen) {
                    composable<TodoListScreen> {
                        TodoListScreen(
                            viewModel = viewModel,
                            onNavigateToAddTodo = {
                                navController.navigate(route = AddTodoScreen)
                            }
                        )
                    }
                    composable<AddTodoScreen> {
                        AddTodoScreen(
                            viewModel = viewModel,
                            onNavigateUp = {
                            navController.navigateUp()
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
