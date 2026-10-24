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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.esucar.ui.components.BarraNavegacionEstudiante

@Composable
fun PantallaMisConsultas(navController: NavController) {

    val verde = Color(0xFF104D30)
    val fondo = Color(0xFFF1F3F0)
    var filtro by remember { mutableStateOf("Todas") }

    val consultas = listOf(
        Triple("Duda ejercicio 4, Guía N°3", "Derecho Penal I · U3", "Respondida"),
        Triple("Formato del informe de la unidad", "Gestión Administrativa · U2", "Sin respuesta"),
        Triple("Lectura complementaria", "Doctrina Institucional · U1", "Resuelta")
    )

    Scaffold(
        containerColor = fondo,
        bottomBar = {
            BarraNavegacionEstudiante(
                seleccionada = "consultas_estudiante",
                onSeleccionar = { destino ->
                    when (destino) {
                        "inicio_estudiante" -> navController.navigate(destino)
                        "mis_asignaturas" -> navController.navigate(destino)
                        "consultas_estudiante" -> Unit
                        else -> Unit
                    }
                }
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
                        text = "Mis consultas",
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
                    onClick = {
                        navController.navigate("nueva_consulta")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = verde
                    )
                ) {
                    Text("+ Nueva", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Todas", "Sin respuesta", "Respondidas").forEach { opcion ->
                    FilterChip(
                        selected = filtro == opcion,
                        onClick = { filtro = opcion },
                        label = { Text(opcion, fontSize = 11.sp) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            consultas.filter { consulta ->
                when (filtro) {
                    "Sin respuesta" -> consulta.third == "Sin respuesta"
                    "Respondidas" -> consulta.third == "Respondida" ||
                            consulta.third == "Resuelta"
                    else -> true
                }
            }.forEach { consulta ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Text(
                            text = consulta.second,
                            fontSize = 11.sp,
                            color = verde
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = consulta.first,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = consulta.third,
                            fontSize = 11.sp,
                            color = if (consulta.third == "Sin respuesta")
                                Color(0xFFE85D18)
                            else
                                verde
                        )
                    }
                }
            }
        }
    }
}
