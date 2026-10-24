package com.example.esucar.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.esucar.ui.components.BarraNavegacionEstudiante
import com.example.esucar.ui.components.EncabezadoEstudiante

data class ProgresoAsignaturaItem(
    val codigo: String,
    val nombre: String,
    val porcentaje: Float,
    val unidadesTotal: Int,
    val unidadesCompletadas: Int
)

@Composable
fun PantallaMiProgreso(navController: NavController) {
    val verde = Color(0xFF104D30)

    val listaProgresos = listOf(
        ProgresoAsignaturaItem("DER-101", "Derecho Penal I", 0.75f, 4, 3),
        ProgresoAsignaturaItem("PRO-201", "Procedimientos Policiales", 0.50f, 4, 2),
        ProgresoAsignaturaItem("DDHH-102", "Derechos Humanos", 0.90f, 3, 3),
        ProgresoAsignaturaItem("TAC-301", "Tácticas Policiales", 0.33f, 3, 1)
    )

    Scaffold(
        topBar = {
            EncabezadoEstudiante(
                nombre = "Carabinero Alumno",
                onNotificaciones = { navController.navigate("notificaciones") }
            )
        },
        bottomBar = {
            BarraNavegacionEstudiante(
                seleccionada = "progreso_estudiante",
                onSeleccionar = { ruta -> navController.navigate(ruta) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF2F4EF))
                .padding(16.dp)
        ) {
            Text(
                text = "Mi Progreso Académico",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = verde,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Resumen General Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = verde),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Avance General del Semestre",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "62%",
                            color = Color(0xFFE5B000),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "9 de 14 Unidades",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LinearProgressIndicator(
                        progress = { 0.62f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp),
                        color = Color(0xFFE5B000),
                        trackColor = Color.White.copy(alpha = 0.3f),
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stat Cards Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.AutoMirrored.Outlined.MenuBook, contentDescription = null, tint = verde)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("24 Doc.", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = verde)
                        Text("Estudiados", fontSize = 11.sp, color = Color.Gray)
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Outlined.QuestionAnswer, contentDescription = null, tint = verde)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("8 Dudas", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = verde)
                        Text("Resueltas", fontSize = 11.sp, color = Color.Gray)
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Outlined.Star, contentDescription = null, tint = Color(0xFFE5B000))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("12 Días", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = verde)
                        Text("Constancia", fontSize = 11.sp, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Detalle por Asignatura",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = verde,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(listaProgresos) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = item.nombre,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = verde
                                    )
                                    Text(
                                        text = "${item.codigo} • ${item.unidadesCompletadas}/${item.unidadesTotal} Unidades",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                                Text(
                                    text = "${(item.porcentaje * 100).toInt()}%",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = verde
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            LinearProgressIndicator(
                                progress = { item.porcentaje },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp),
                                color = verde,
                                trackColor = Color(0xFFE0E0E0),
                            )
                        }
                    }
                }
            }
        }
    }
}
