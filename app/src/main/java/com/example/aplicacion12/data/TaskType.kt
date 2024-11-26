package com.example.aplicacion12.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_types")
data class TaskType(
    @PrimaryKey(autoGenerate = true) // ID autogenerado
    val id: Int,                    // Nueva clave primaria
    val title: String               // Nombre del tipo de tarea
)
