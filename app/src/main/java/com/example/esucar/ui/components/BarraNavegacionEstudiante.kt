package com.example.esucar.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BarraNavegacionEstudiante(
    seleccionada: String,
    onSeleccionar: (String) -> Unit
) {
    val verde = Color(0xFF104D30)

    NavigationBar(
        containerColor = Color.White
    ) {
        NavigationBarItem(
            selected = seleccionada == "inicio_estudiante",
            onClick = { onSeleccionar("inicio_estudiante") },
            icon = {
                Icon(Icons.Outlined.Home, contentDescription = "Inicio")
            },
            label = { Text("Inicio") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = verde,
                selectedTextColor = verde
            )
        )

        NavigationBarItem(
            selected = seleccionada == "mis_asignaturas",
            onClick = { onSeleccionar("mis_asignaturas") },
            icon = {
                Icon(Icons.Outlined.MenuBook, contentDescription = "Asignaturas")
            },
            label = { Text("Asignaturas") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = verde,
                selectedTextColor = verde
            )
        )

        NavigationBarItem(
            selected = seleccionada == "consultas_estudiante",
            onClick = { onSeleccionar("consultas_estudiante") },
            icon = {
                Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = "Consultas")
            },
            label = { Text("Consultas") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = verde,
                selectedTextColor = verde
            )
        )

        NavigationBarItem(
            selected = seleccionada == "progreso_estudiante",
            onClick = { onSeleccionar("progreso_estudiante") },
            icon = {
                Icon(Icons.Outlined.BarChart, contentDescription = "Progreso")
            },
            label = { Text("Progreso") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = verde,
                selectedTextColor = verde
            )
        )
    }
}
