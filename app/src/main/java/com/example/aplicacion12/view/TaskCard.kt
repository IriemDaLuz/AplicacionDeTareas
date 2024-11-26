package com.example.aplicacion12.view

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
            targetValue = if (expanded) 48.dp else 18.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )

        Row(
            modifier = Modifier.padding(vertical = animatedPadding, horizontal = 8.dp),
        ) {


            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text("Id Tarea:" + task.id.toString())
                Row(modifier = Modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = task.name, fontWeight = FontWeight.Bold)
                    Text(modifier = Modifier.padding(start = 20.dp),text="Id de tipo de Tarea:"+task.id_tipostareas.toString())
                }
                if (expanded) {
                    Text(descVisibility)
                }
                Button(onClick = {
                    expanded = !expanded
                    if (expanded) {
                        descVisibility = task.description.toString()
                    }
                },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(if (expanded) "Cerrar Descripción" else "Mostrar descripción")
                }
            }
            Spacer(modifier = Modifier.width(16.dp))

            Column(horizontalAlignment = Alignment.End) {
                // Edit button
                Button(
                    onClick = { onUpdate(task) },
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
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
    }
}
