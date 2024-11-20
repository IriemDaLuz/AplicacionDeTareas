package com.example.composeroomapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeroomapp.data.Task
import com.example.composeroomapp.data.TaskDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val taskDao: TaskDao) : ViewModel() {

    // Estado que contiene la lista de tareas
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    // Cargar todas las tareas desde la base de datos
    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = taskDao.getAllTasks()
        }
    }

    // Agregar una nueva tarea
    fun addTask(name: String) {
        if (name.isBlank()) return

        viewModelScope.launch {
            val newTask = Task(name = name)
            taskDao.insert(newTask)
            loadTasks() // Recargar las tareas después de agregar
        }
    }

    // Actualizar una tarea existente
    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskDao.update(task)
            loadTasks() // Recargar las tareas después de actualizar
        }
    }

    // Eliminar una tarea
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.delete(task)
            loadTasks() // Recargar las tareas después de eliminar
        }
    }
}
