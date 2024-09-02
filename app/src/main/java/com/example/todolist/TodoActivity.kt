package com.example.todolist

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.ScaffoldDefaults
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import com.example.todo.ui.navigation.TodoNavigationGraph
import com.example.todolist.ui.theme.TodoListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            TodoListTheme {
                ScaffoldDefaults.contentWindowInsets

                TodoNavigationGraph()
            }
        }
    }
}
