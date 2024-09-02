package com.example.todo.ui.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PopupDialog(
    isVisible: Boolean,
    title: String = "Error",
    message: String,
    onDismiss: () -> Unit,
) {
    if (isVisible) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(title) },
            text = { Text(message) },
            confirmButton = {
                Button(onClick = { onDismiss() }) {
                    Text("OK")
                }
            }
        )
    }
}