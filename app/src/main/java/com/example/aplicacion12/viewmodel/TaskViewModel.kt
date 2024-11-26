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

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    private val _taskTypes = MutableStateFlow<List<TaskType>>(emptyList())
    val taskTypes: StateFlow<List<TaskType>> = _taskTypes

    init {
        loadTasks()
        loadTaskTypes()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = taskDao.getAllTasks()
        }
    }

    private fun loadTaskTypes() {
        viewModelScope.launch {
            _taskTypes.value = taskTypeDao.getAllTaskTypes()
        }
    }

    fun addTask(name: String, typeTitle: String, description: String) {
        if (name.isBlank() || typeTitle.isBlank()) return

        viewModelScope.launch {
            try {
                val taskType = taskTypeDao.getTaskTypeByTitle(typeTitle)
                if (taskType != null) {
                    val newTask = Task(
                        id = 0,
                        name = name,
                        id_tipostareas = taskType.id.toLong(),
                        description = description
                    )
                    taskDao.insert(newTask)
                    loadTasks() // Recargar tareas
                } else {
                    Log.e("TaskViewModel", "Tipo de tarea no encontrado: $typeTitle")
                }
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al añadir tarea: ${e.message}")
            }
        }
    }

    fun addTaskType(title: String) {
        if (title.isBlank()) return

        viewModelScope.launch {
            try {
                val newTaskType = TaskType(id = 0, title = title)
                taskTypeDao.insert(newTaskType)
                loadTaskTypes() // Recargar tipos de tareas
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al añadir tipo de tarea: ${e.message}")
            }
        }
    }

    // Eliminar un tipo de tarea
    fun deleteTaskType(taskType: TaskType) {
        viewModelScope.launch {
            try {
                taskTypeDao.delete(taskType)
                loadTaskTypes() // Recargar tipos de tareas después de eliminar
                loadTasks()
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al eliminar tipo de tarea: ${e.message}")
            }
        }
    }

    // Actualizar un tipo de tarea
    fun updateTaskType(updatedTaskType: TaskType) {
        viewModelScope.launch {
            try {
                taskTypeDao.update(updatedTaskType)
                loadTaskTypes() // Recargar tipos de tareas después de actualizar
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al actualizar tipo de tarea: ${e.message}")
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                taskDao.delete(task)
                loadTasks() // Recargar tareas después de eliminar
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al eliminar tarea: ${e.message}")
            }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            try {
                taskDao.update(task)
                loadTasks() // Recargar tareas después de actualizar
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error al actualizar tarea: ${e.message}")
            }
        }
    }
}
