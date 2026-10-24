package com.example.esucar.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.esucar.database.DatabaseHelper
import com.example.esucar.ui.components.BarraNavegacionEstudiante

@Composable
fun PantallaMisConsultas(navController: NavController) {
    val context = LocalContext.current
    val databaseHelper = remember(context) { DatabaseHelper(context.applicationContext) }

    val verde = Color(0xFF104D30)
    val fondo = Color(0xFFF1F3F0)
    var filtro by remember { mutableStateOf("Todas") }

    val consultas = remember { databaseHelper.obtenerConsultas() }

    val consultasFiltradas = consultas.filter { c ->
        when (filtro) {
            "Sin respuesta" -> c.estado == "Pendiente"
            "Respondidas" -> c.estado == "Respondida"
            else -> true
        }
    }

    Scaffold(
        containerColor = fondo,
        bottomBar = {
            BarraNavegacionEstudiante(
                seleccionada = "consultas_estudiante",
                onSeleccionar = { destino -> navController.navigate(destino) }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Mis Consultas",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF172B20)
                    )
                    Text(
                        text = "Canal directo con tus docentes",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Button(
                    onClick = { navController.navigate("nueva_consulta") },
                    colors = ButtonDefaults.buttonColors(containerColor = verde)
                ) {
                    Text("+ Nueva", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Todas", "Sin respuesta", "Respondidas").forEach { opcion ->
                    FilterChip(
                        selected = filtro == opcion,
                        onClick = { filtro = opcion },
                        label = { Text(opcion, fontSize = 11.sp) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (consultasFiltradas.isEmpty()) {
                Text(
                    text = "No tienes consultas registradas",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 16.dp)
                )
            } else {
                consultasFiltradas.forEach { consulta ->
                    Card(
                        onClick = {
                            navController.navigate("conversacion/${consulta.id}?perfil=Estudiante")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Asignatura: ${consulta.codigoAsignatura}",
                                    fontSize = 11.sp,
                                    color = verde,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = consulta.fecha,
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = consulta.pregunta,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.DarkGray
                            )

                            if (consulta.respuesta != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Respuesta del profesor: ${consulta.respuesta}",
                                    fontSize = 12.sp,
                                    color = Color(0xFF2E7D32)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = if (consulta.estado == "Pendiente") "Pendiente de respuesta" else "Respondida por docente",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (consulta.estado == "Pendiente") Color(0xFFE85D18) else verde
                            )
                        }
                    }
                }
            }
        }
    }
}
