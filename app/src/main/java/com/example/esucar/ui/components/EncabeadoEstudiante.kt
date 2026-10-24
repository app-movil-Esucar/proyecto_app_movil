package com.example.esucar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EncabezadoEstudiante(
    nombre: String,
    onNotificaciones: () -> Unit,
    onCerrarSesion: (() -> Unit)? = null
) {
    val verde = Color(0xFF104D30)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(verde)
            .padding(horizontal = 18.dp, vertical = 22.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Hola,",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 12.sp
            )
            Text(
                text = nombre,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onNotificaciones,
                modifier = Modifier.background(
                    Color.White.copy(alpha = 0.12f),
                    RoundedCornerShape(12.dp)
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notificaciones",
                    tint = Color.White
                )
            }

            if (onCerrarSesion != null) {
                IconButton(
                    onClick = onCerrarSesion,
                    modifier = Modifier.background(
                        Color.White.copy(alpha = 0.12f),
                        RoundedCornerShape(12.dp)
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                        contentDescription = "Cerrar Sesión",
                        tint = Color(0xFFFFCDD2)
                    )
                }
            }
        }
    }
}
