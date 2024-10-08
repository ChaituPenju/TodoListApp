package com.example.todo.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.todo.domain.models.TodoItem

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
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TodoListItemPreview() {
    TodoListItem(
        todoItem = TodoItem(
            title = "Example Title",
        )
    )
}