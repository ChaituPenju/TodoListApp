package com.example.todolist.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolist.models.TodoItem
import com.example.todolist.ui.theme.TodoListTheme

@Composable
fun TodoListItem(
    modifier: Modifier = Modifier,
    todoItem: TodoItem,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(all = 4.dp),
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = 4.dp,
        ),
        shape = RectangleShape,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
        ) {
            Text(
                text = todoItem.title,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = todoItem.description,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TodoListItemPreview() {
    TodoListTheme {
        TodoListItem(
            todoItem = TodoItem(
                title = "Example Title",
                description = "Example description is the description",
                isFinished = false
            )
        )
    }
}