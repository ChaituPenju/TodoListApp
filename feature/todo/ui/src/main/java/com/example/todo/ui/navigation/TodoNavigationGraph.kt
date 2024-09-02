package com.example.todo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo.ui.screens.AddTodoScreen
import com.example.todo.ui.screens.TodoListScreen
import com.example.todo.ui.viewmodels.TodoScreensViewModel

@Composable
fun TodoNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Any = TodoListScreen,
    navActions: TodoNavigationActions = remember(navController) {
        TodoNavigationActions(navController = navController)
    },
    viewModel: TodoScreensViewModel = hiltViewModel<TodoScreensViewModel>(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<TodoListScreen> {
            TodoListScreen(
                viewModel = viewModel,
                onNavigateToAddTodo = {
                    navActions.navigateToAddTodoScreen()
                }
            )
        }
        composable<AddTodoScreen> {
            AddTodoScreen(
                viewModel = viewModel,
                onNavigateUp = {
                    navActions.navigateUp()
                },
            )
        }
    }
}