package com.example.aplicacion12.view

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
Editamosimport androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacion12.data.Task

@Composable
fun TaskCard(
    task: Task,
    onDelete: (Task) -> Unit,
    onUpdate: (Task) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var descVisibility by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        val animatedPadding by animateDpAsState(
            targetValue = if (expanded) 24.dp else 15.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )

        Row(
            modifier = Modifier.padding(vertical = animatedPadding, horizontal = 8.dp),
        ) {


            Column(

            ) {
                Row(modifier = Modifier) {
                    Text(text = task.id.toString() + ". " +task.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(modifier = Modifier.padding(start = 60.dp),text="Id de tipo de Tarea:"+task.id_tipostareas.toString())
                }
                if (expanded) {
                    Text(modifier = Modifier.padding(start=30.dp,15.dp, bottom = 15.dp),text=descVisibility)
                }
                Row() {
                    Button(
                        onClick = {
                            expanded = !expanded
                            if (expanded) {
                                descVisibility = task.description.toString()
                            }
                        },
                        modifier = Modifier.padding(start = 8.dp, bottom = 0.dp)
                    ) {
                        Text(if (expanded) "Cerrar Descripción" else "Mostrar descripción")
                    }
                    Button(modifier = Modifier.padding(start = 8.dp, bottom = 0.dp ),onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Editar",
                            tint = Color.White
                        )
                    }

                    // Delete button
                    Button(onClick = { onDelete(task) }) {
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
