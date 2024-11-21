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

    // MutableStateFlow para almacenar la lista de tareas
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    // MutableStateFlow para almacenar la lista de tipos de tareas
    private val _taskTypes = MutableStateFlow<List<TaskType>>(emptyList())
    val taskTypes: StateFlow<List<TaskType>> = _taskTypes

    // Inicializa la carga de tareas y tipos de tareas
    init {
        loadTasks()
        loadTaskTypes()
    }

    // Carga todas las tareas desde la base de datos
    private fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = taskDao.getAllTasks() // Asegúrate de que esta función sea suspendida
        }
    }

    // Carga todos los tipos de tareas desde la base de datos
    private fun loadTaskTypes() {
        viewModelScope.launch {
            _taskTypes.value = taskTypeDao.getAllTaskTypes() // Asegúrate de que esta función sea suspendida
        }
    }

    // Agrega una nueva tarea
    fun addTask(name: String, typeId: Int) {
        if (name.isBlank()) return

        viewModelScope.launch {
            val newTask = Task(name = name, id_tipostareas = typeId)
            taskDao.insert(newTask) // Asegúrate de que esta función sea suspendida
            loadTasks() // Recarga la lista de tareas
        }
    }

    // Agrega un nuevo tipo de tarea
    fun addTaskType(title: String) {
        if (title.isBlank()) return

        viewModelScope.launch {
            val newTaskType = TaskType(title = title)
            taskTypeDao.insert(newTaskType) // Asegúrate de que esta función sea suspendida
            loadTaskTypes() // Recarga la lista de tipos de tareas
        }
    }
}