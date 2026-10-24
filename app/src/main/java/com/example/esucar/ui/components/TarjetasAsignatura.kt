package com.example.esucar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable

@Composable
fun TarjetaAsignatura(
    codigo: String,
    nombre: String,
    unidadActual: Int,
    totalUnidades: Int,
    progreso: Int,
    onClick: () -> Unit = {}
) {
    val verde = Color(0xFF104D30)
    val porcentaje = progreso.coerceIn(0, 100)

    Card(
        modifier = Modifier.fillMaxWidth()
            .clickable {onClick()},
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Text(
                text = codigo,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = verde,
                modifier = Modifier
                    .background(
                        Color(0xFFE0EEE5),
                        RoundedCornerShape(5.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 3.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = nombre,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF172B20),
                minLines = 2
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Unidad $unidadActual de $totalUnidades",
                fontSize = 11.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { porcentaje / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp),
                color = verde,
                trackColor = Color(0xFFE1E6E2),
                strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
            )
        }
    }
}

