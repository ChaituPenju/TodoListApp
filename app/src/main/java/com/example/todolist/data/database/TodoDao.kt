package com.example.todolist.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.data.models.TodoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * from todos")
    fun getAllTodos(): Flow<List<TodoItem>>

    @Query("SELECT * FROM todos WHERE title LIKE :itemTitle")
    fun getTodosByTitle(itemTitle: String): List<TodoItem>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTodo(item: TodoItem)

    @Insert
    suspend fun insertTodo(item: TodoItem)

    @Delete
    suspend fun deleteTodo(item: TodoItem)
}