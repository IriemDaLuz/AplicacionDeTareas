package com.example.aplicacion12.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = TaskType::class,
            parentColumns = ["title"],
            childColumns = ["title"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val name: String,
    val description: String? = null,
    //titulo de tabla tipo tarea
    val title: String
)
