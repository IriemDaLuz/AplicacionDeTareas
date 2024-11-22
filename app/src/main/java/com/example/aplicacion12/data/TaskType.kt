
package com.example.aplicacion12.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_types")
data class TaskType(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val title: String
)
