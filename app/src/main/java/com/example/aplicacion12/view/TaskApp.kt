package com.example.aplicacion12.view

import TaskViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TaskApp(viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    var newTaskName by remember { mutableStateOf("") }
    var selectedTaskTypeName by remember { mutableStateOf("") }
    var newTaskDescrip by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Añadir nuevo tipo de Tareas",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            modifier = Modifier.padding(top = 38.dp, bottom = 46.dp)
        )

        Text("Crear nuevo tipo de Tarea")
        Row {
            OutlinedTextField(
                value = selectedTaskTypeName,
                onValueChange = { selectedTaskTypeName = it },
                label = { Text("Tipo de Tarea") },
                placeholder = { Text("Crear tipo de tarea...") },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Button(onClick = { viewModel.addTaskType(selectedTaskTypeName) }) {
                Text("+")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Crear nueva tarea")
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = newTaskName,
                onValueChange = { newTaskName = it },
                label = { Text("Titulo de Tarea") },
                placeholder = { Text("Pon el nombre de la tarea...") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = newTaskDescrip,
                onValueChange = { newTaskDescrip = it },
                label = { Text("Descripcion") },
                placeholder = { Text("Pon la descripcion de la tarea...") },
                modifier = Modifier.weight(1f) // Usar el mismo principio para la descripción
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (newTaskName.isNotBlank() && selectedTaskTypeName.isNotBlank()) {
                    viewModel.addTask(newTaskName, selectedTaskTypeName, newTaskDescrip)
                    newTaskName = ""
                    selectedTaskTypeName = ""
                    newTaskDescrip = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Añadir")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tasks.forEach { task ->
                TaskCard(task)
            }
        }
    }
}
