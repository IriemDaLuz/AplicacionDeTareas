package com.example.aplicacion12.view

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacion12.data.Task
import com.example.aplicacion12.data.TaskType

@Composable
fun TaskCard(
    task: Task,
    taskTypes: List<TaskType>,
    onDelete: (Task) -> Unit,
    onUpdate: (Task) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var descVisibility by remember { mutableStateOf("") }

    // Obtener el nombre del tipo de tarea
    val taskTypeName = taskTypes.find { it.id.toLong() == task.id_tipostareas }?.title ?: "Sin tipo"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        val animatedPadding by animateDpAsState(
            targetValue = if (expanded) 24.dp else 15.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            )
        )

        Row(
            modifier = Modifier.padding(vertical = animatedPadding, horizontal = 8.dp),
        ) {
            Column {
                Row(modifier = Modifier) {
                    Text(
                        text = "${task.id}. ${task.name}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        modifier = Modifier.padding(start = 60.dp),
                        text = "Tipo de Tarea: $taskTypeName"
                    )
                }
                if (expanded) {
                    Text(
                        modifier = Modifier.padding(start = 30.dp, top = 15.dp, bottom = 15.dp),
                        text = descVisibility
                    )
                }
                Row {
                    Button(
                        onClick = {
                            expanded = !expanded
                            if (expanded) {
                                descVisibility = task.description.toString()
                            }
                        },
                        modifier = Modifier.padding(start = 8.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFFFCC35))
                    ) {
                        Text(if (expanded) "Cerrar Descripción" else "Mostrar descripción")
                    }
                    Button(
                        modifier = Modifier.padding(start = 8.dp),
                        onClick = { onUpdate(task) },
                        colors = ButtonDefaults.buttonColors(Color(0xFFFFCC35))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Editar",
                            tint = Color.White
                        )
                    }
                    Button(
                        onClick = { onDelete(task) },
                        colors = ButtonDefaults.buttonColors(Color(0xFFFFCC35))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Eliminar",
                            tint = Color.White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}
