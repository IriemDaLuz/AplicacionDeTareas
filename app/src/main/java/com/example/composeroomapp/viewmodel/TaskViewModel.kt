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

    fun addTask(name: String, typeId: Int) {
        if (name.isBlank()) return

        viewModelScope.launch {
            val newTask = Task(name = name, id_tipostareas = typeId)
            taskDao.insert(newTask)
            loadTasks()
        }
    }

    fun addTaskType(title: String) {
        if (title.isBlank()) return

        viewModelScope.launch {
            val newTaskType = TaskType(title = title)
            taskTypeDao.insert(newTaskType)
            loadTaskTypes()
        }
    }
}
