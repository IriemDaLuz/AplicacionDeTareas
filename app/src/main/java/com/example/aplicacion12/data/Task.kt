package com.example.aplicacion12.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = TaskType::class,
            parentColumns = ["id"], // Clave primaria en la tabla relacionada
            childColumns = ["id_tipostareas"], // Clave foránea
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val id_tipostareas: Long,
    val description: String, // Ensure this is not nullable
    val isCompleted: Boolean = false // Ensure this is included if needed
)