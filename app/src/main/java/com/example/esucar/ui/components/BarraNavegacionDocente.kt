package com.example.esucar.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BarraNavegacionDocente(
    seleccionada: String,
    onSeleccionar: (String) -> Unit
) {
    val verde = Color(0xFF104D30)

    NavigationBar(
        containerColor = Color.White
    ) {
        NavigationBarItem(
            selected = seleccionada == "inicio_docente",
            onClick = { onSeleccionar("inicio_docente") },
            icon = {
                Icon(Icons.Outlined.Home, contentDescription = "Inicio Docente")
            },
            label = { Text("Inicio") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = verde,
                selectedTextColor = verde
            )
        )

        NavigationBarItem(
            selected = seleccionada == "consultas_docente",
            onClick = { onSeleccionar("consultas_docente") },
            icon = {
                Icon(Icons.Outlined.Inbox, contentDescription = "Consultas Recibidas")
            },
            label = { Text("Consultas") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = verde,
                selectedTextColor = verde
            )
        )

        NavigationBarItem(
            selected = seleccionada == "materiales_docente",
            onClick = { onSeleccionar("materiales_docente") },
            icon = {
                Icon(Icons.AutoMirrored.Outlined.LibraryBooks, contentDescription = "Gestión Materiales")
            },
            label = { Text("Materiales") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = verde,
                selectedTextColor = verde
            )
        )
    }
}
