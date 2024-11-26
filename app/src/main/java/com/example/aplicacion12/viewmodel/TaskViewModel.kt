package com.example.aplicacion12.viewmodel.Task

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacion12.data.Task
import com.example.aplicacion12.data.TaskDao
import com.example.aplicacion12.data.TaskType
import com.example.aplicacion12.data.TaskTypeDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val taskDao: TaskDao, private val taskTypeDao: TaskTypeDao) : ViewModel() {

    // Flujo de tareas
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    // Flujo de tipos de tareas
    private val _taskTypes = MutableStateFlow<List<TaskType>>(emptyList())
    val taskTypes: StateFlow<List<TaskType>> = _taskTypes

    // Inicialización: Cargar las tareas y tipos de tareas
    init {
        loadTasks()
        loadTaskTypes()
    }

    // Cargar las tareas desde el DAO
    private fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = taskDao.getAllTasks()
        }
    }

    // Cargar los tipos de tareas desde el DAO
    private fun loadTaskTypes() {
        viewModelScope.launch {
            _taskTypes.value = taskTypeDao.getAllTaskTypes()
        }
    }

    // Agregar una nueva tarea
    fun addTask(name: String, typeTitle: String, description: String) {
        if (name.isBlank() || typeTitle.isBlank()) return

        viewModelScope.launch {
            try {
                // Buscar el tipo de tarea por título
                val taskType = taskTypeDao.getTaskTypeByTitle(typeTitle)
                if (taskType != null) {
                    // Crear una nueva tarea y guardarla
                    val newTask = Task(
                        id = 0,
                        name = name,
                        id_tipostareas = taskType.id.toLong(), // Convert Int to Long
                        description = description
                    )
                    taskDao.insert(newTask)
                } else {
                    Log.e("TaskViewModel", "Tipo de tarea no encontrado: $typeTitle")
                }
            } catch (e: Exception) {                Log.e("TaskViewModel", "Error al añadir tarea: ${e.message}")
            }
        }
    }

    // Agregar un nuevo tipo de tarea
    fun addTaskType(title: String) {
        if (title.isBlank()) return

        viewModelScope.launch {
            try {
                // Crear un nuevo tipo de tarea y guardarlo
                val newTaskType = TaskType(id = 0, title = title) // 'id = 0' se autogenerará si está configurado en la entidad.
                taskTypeDao.insert(newTaskType)
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al añadir tipo de tarea: ${e.message}")
            }
        }
    }
}