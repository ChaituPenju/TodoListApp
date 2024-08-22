package com.example.todolist.models

data class TodoItem(
    val title: String,
    val description: String,
    val isFinished: Boolean,
)