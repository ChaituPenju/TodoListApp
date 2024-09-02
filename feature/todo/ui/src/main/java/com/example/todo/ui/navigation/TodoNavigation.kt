package com.example.todo.ui.navigation

import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable


@Serializable
object TodoListScreen

@Serializable
object AddTodoScreen

class TodoNavigationActions(
    private val navController: NavHostController,
) {
    fun navigateToAddTodoScreen() {
        navController.navigate(route = AddTodoScreen)
    }

    fun navigateUp() {
        navController.navigateUp()
    }
}