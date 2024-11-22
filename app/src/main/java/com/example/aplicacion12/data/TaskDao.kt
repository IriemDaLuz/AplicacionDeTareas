package com.example.aplicacion12.data

import androidx.room.*

@Dao
interface TaskDao {

    // Obtener todas las tareas
    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<Task>

    // Insertar una nueva tarea
    @Insert(onConflict = OnConflictStrategy.REPLACE) // Evitar duplicados
    suspend fun insert(task: Task)

    // Actualizar una tarea existente
    @Update
    suspend fun update(task: Task)

    // Eliminar una tarea específica
    @Delete
    suspend fun delete(task: Task)

    // Obtener una tarea específica por ID (si es necesario en el futuro)
    @Query("SELECT * FROM tasks WHERE id = :taskId LIMIT 1")
    suspend fun getTaskById(taskId: Int): Task?
}