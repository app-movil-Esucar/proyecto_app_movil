package com.example.esucar.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TarjetaAvanceSemestre(
    porcentaje: Int,
    revisados: Int,
    pendientes: Int,
    consultas: Int,
    semestre: String,
    asignaturas: Int
) {
    val verde = Color(0xFF104D30)
    val naranja = Color(0xFFE85D18)
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(70.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawArc(
                            color = Color(0xFFE5EAE6),
                            startAngle = -90f,
                            sweepAngle = 360f,
                            useCenter = false,
                            style = Stroke(
                                width = 8.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        )
                        drawArc(
                            color = verde,
                            startAngle = -90f,
                            sweepAngle = 360f * porcentaje.coerceIn(0, 100) / 100f,
                            useCenter = false,
                            style = Stroke(
                                width = 8.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        )
                    }
                    Text(
                        text = "${porcentaje.coerceIn(0, 100)}%",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = verde
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Mi avance del semestre",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color(0xFF172B20)
                    )
                    Text(
                        text = "$semestre · $asignaturas asignaturas",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                color = Color(0xFFE5EAE6)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IndicadorAvance(
                    cantidad = revisados,
                    etiqueta = "Revisados",
                    color = verde
                )
                IndicadorAvance(
                    cantidad = pendientes,
                    etiqueta = "Pendientes",
                    color = naranja
                )
                IndicadorAvance(
                    cantidad = consultas,
                    etiqueta = "Consultas abiertas",
                    color = Color(0xFF172B20)
                )
            }
        }
    }
}

@Composable
private fun IndicadorAvance(
    cantidad: Int,
    etiqueta: String,
    color: Color
) {
    Column {
        Text(
            text = cantidad.toString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = etiqueta,
            fontSize = 10.sp,
            color = Color.Gray
        )
    }
}
