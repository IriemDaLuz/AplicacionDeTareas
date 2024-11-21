package com.example.aplicacion12.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = TaskType::class,
            parentColumns = ["id"],
            childColumns = ["id_tipostareas"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val name: String,
    val description: String? = null,
    val id_tipostareas: Int
)
