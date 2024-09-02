package com.example.todo.data.mappers

import com.example.todo.data.models.TodoItemDto
import com.example.todo.domain.models.TodoItem

fun TodoItemDto.toTodoItem(): TodoItem {
    return TodoItem(
        id = this.id,
        title = this.title,
    )
}