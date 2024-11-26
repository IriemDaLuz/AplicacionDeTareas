package com.example.aplicacion12.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aplicacion12.data.TaskDao
import com.example.aplicacion12.data.TaskTypeDao
import com.example.aplicacion12.viewmodel.Task.TaskViewModel

class TaskViewModelFactory(
    private val taskDao: TaskDao,
    private val taskTypeDao: TaskTypeDao,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(taskDao, taskTypeDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
