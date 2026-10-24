
package com.example.esucar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SelectorPerfil(
    tipoUsuario: String,
    onSeleccionar: (String) -> Unit
) {
    val verde = Color(0xFF104D30)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFFE4EAE4),
                RoundedCornerShape(10.dp)
            )
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        listOf("Estudiante", "Docente").forEach { usuario ->
            Button(
                onClick = {
                    onSeleccionar(usuario)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        if (tipoUsuario == usuario)
                            Color.White
                        else
                            Color(0xFFE4EAE4),
                    contentColor =
                        if (tipoUsuario == usuario)
                            verde
                        else
                            Color.DarkGray
                ),
                contentPadding = PaddingValues(0.dp),
                elevation = null
            ) {
                Text(
                    text = usuario,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


