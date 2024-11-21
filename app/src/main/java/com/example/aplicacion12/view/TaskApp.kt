package com.example.aplicacion12.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TaskApp(viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    var newTaskName by remember { mutableStateOf("") }
    var selectedTaskType by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = newTaskName,
            onValueChange = { newTaskName = it },
            label = { Text("New Task") },
            placeholder = { Text("Enter task name...") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = selectedTaskType.toString(),
            onValueChange = { selectedTaskType = it.toIntOrNull() ?: 0 },
            label = { Text("Task Type ID") },
            placeholder = { Text("Enter task type ID...") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (newTaskName.isNotBlank()) {
                    viewModel.addTask(newTaskName, selectedTaskType)
                    newTaskName = ""
                    selectedTaskType = 0
                }
            },
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        ) {
            Text("Añadir Tareas")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Usar Column en lugar de LazyColumn
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (task in tasks) {
                TaskCard(task)
            }
        }
    }
}