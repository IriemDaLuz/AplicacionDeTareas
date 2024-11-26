package com.example.aplicacion12.view

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aplicacion12.data.Task

@Composable
fun TaskCard(
    task: Task
) {
    var expanded by remember { mutableStateOf(false) }
    var descvisibilidad by remember { mutableStateOf("") }

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
            Text(task.id.toString())
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(modifier = Modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = task.name, fontWeight = FontWeight.Bold)
                    Text(task.id_tipostareas.toString())
                }
                if (expanded) {
                    Text(descvisibilidad)
                }
                Button(onClick = { expanded = !expanded },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    if (expanded) {
                        descvisibilidad = task.description.toString()
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(if (expanded) "Cerrar Descripcion" else "Mostrar descripcion")
                }

            }
            Column {
                Button({}) {
                    Text("Actualizar")
                }
                Button({}) {
                    Text("Borrar")
                }
            }
        }
    }
}