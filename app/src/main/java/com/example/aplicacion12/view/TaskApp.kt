package com.example.aplicacion12.view

import android.R
import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacion12.data.Task
import com.example.aplicacion12.viewmodel.Task.TaskViewModel

@Composable
fun TaskApp(viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    var newTaskName by remember { mutableStateOf("") }
    var selectedTaskTypeName by remember { mutableStateOf("") }
    var newTaskDescrip by remember { mutableStateOf("") }
    val taskTypes by viewModel.taskTypes.collectAsState()
    var showTaskTypes by remember { mutableStateOf(false) }

    // Variable para saber si estamos editando una tarea
    var modoEdicion by remember { mutableStateOf<Task?>(null) }

    LaunchedEffect(tasks) {
        Log.d("TaskApp", "Tasks updated: $tasks")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(
            text = "Gestor de Tareas",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 18.dp, bottom = 24.dp)
        )

        // Crear o seleccionar tipo de tarea
        Text("Crear nuevo tipo de Tarea")
        Row {
            OutlinedTextField(
                value = selectedTaskTypeName,
                onValueChange = { selectedTaskTypeName = it },
                label = { Text("Tipo de Tarea") },
                placeholder = { Text("Crear o seleccionar tipo de tarea...") },
                modifier = Modifier.weight(3f)
            )
            Column(modifier = Modifier.weight(1.5f).padding(start = 20.dp)) {
                Button(
                    onClick = { viewModel.addTaskType(selectedTaskTypeName) },
                    modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Color(0xFFFFCC35))
                ) {
                    Text("Añadir")
                }
                Button(
                    onClick = { showTaskTypes = !showTaskTypes },
                    modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Color(0xFFFFCC35))
                ) {
                    Text(if (showTaskTypes) "Cerrar" else "Mostrar")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Crear nueva tarea o editar tarea existente
        Text(if (modoEdicion == null) "Crear nueva tarea" else "Editar tarea")
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
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (newTaskName.isNotBlank() && selectedTaskTypeName.isNotBlank()) {
                    if (modoEdicion == null) {
                        // Si no estamos editando, crear nueva tarea
                        viewModel.addTask(newTaskName, selectedTaskTypeName, newTaskDescrip)
                    } else {
                        // Si estamos editando, actualizar la tarea
                        val updatedTask = modoEdicion!!.copy(
                            name = newTaskName,
                            description = newTaskDescrip,
                            id_tipostareas = taskTypes.find { it.title == selectedTaskTypeName }?.id?.toLong() ?: 0
                        )
                        viewModel.updateTask(updatedTask)
                    }
                    // Limpiar campos después de agregar o editar
                    newTaskName = ""
                    selectedTaskTypeName = ""
                    newTaskDescrip = ""
                    modoEdicion = null // Reseteamos la tarea en edición
                }
            },
            modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Color(0xFFFFCC35))
        ) {
            Text(if (modoEdicion == null) "Agregar Tarea" else "Confirmar edición")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar tipos de tareas existentes
        if (showTaskTypes) {

            val animatedPadding by animateDpAsState(
                targetValue = if (showTaskTypes) 24.dp else 15.dp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            Text("Tipos de Tareas Existentes:", fontWeight = FontWeight.Bold)
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(taskTypes) { taskType ->
                    Row(modifier = Modifier.fillMaxWidth().background(color=Color(0xFFFFCC35)), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(taskType.title)
                        // Muestra el título de cada tipo de tarea
                        Row (modifier = Modifier.padding( end = 10.dp)){
                            IconButton(onClick = {viewModel.updateTaskType(taskType)}){
                                Icon(Icons.Filled.Build, contentDescription = "Editar")
                            }
                            IconButton(onClick = {viewModel.deleteTaskType(taskType)}){
                                Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
                            }
                        }
                    }

                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar tareas
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks) { task ->
                TaskCard(
                    task = task,
                    onDelete = { viewModel.deleteTask(task) },
                    onUpdate = { updatedTask ->
                        // Configurar campos para la edición
                        modoEdicion = updatedTask
                        newTaskName = updatedTask.name
                        selectedTaskTypeName = taskTypes.find { it.id.toLong() == updatedTask.id_tipostareas }?.title ?: ""
                        newTaskDescrip = updatedTask.description
                    }
                )
            }
        }
    }
}
