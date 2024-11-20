package com.example.composeroomapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composeroomapp.data.Task
import com.example.composeroomapp.viewmodel.TaskViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TaskApp(viewModel: TaskViewModel) {
    var tasks by remember { mutableStateOf(listOf<Task>()) }
    var newTaskName by remember { mutableStateOf("") }

    // Observar cambios en las tareas
    LaunchedEffect(Unit) {
        viewModel.tasks.collectLatest {
            tasks = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Campo de texto para agregar una nueva tarea
        OutlinedTextField(
            value = newTaskName,
            onValueChange = { newTaskName = it },
            label = { Text("New Task") },
            modifier = Modifier.fillMaxWidth()
        )

        // Botón para agregar tarea
        Button(onClick = {
            viewModel.addTask(newTaskName)
            newTaskName = "" // Limpiar el campo después de agregar
        }) {
            Text("Add Task")
        }

        // Mostrar lista de tareas
        tasks.forEach { task ->
            Text(text = task.name)
        }
    }
}
