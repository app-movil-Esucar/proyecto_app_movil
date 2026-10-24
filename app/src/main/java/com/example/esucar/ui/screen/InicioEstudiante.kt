
package com.example.esucar.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.example.esucar.ui.components.EncabezadoEstudiante
import com.example.esucar.ui.components.TarjetaAvanceSemestre
import com.example.esucar.ui.components.TarjetaAsignatura
import com.example.esucar.ui.components.BarraNavegacionEstudiante

@Composable
fun PantallaInicioEstudiante(navController: NavController) {

    val fondo = Color(0xFFF1F3F0)
    val verde = Color(0xFF104D30)

    Scaffold(
        bottomBar = {
            BarraNavegacionEstudiante(
                seleccionada = "inicio_estudiante",
                onSeleccionar = { ruta ->
                    when (ruta) {
                        "mis_asignaturas" -> {
                            navController.navigate("mis_asignaturas")
                        }
                        "consultas_estudiante" -> {
                            navController.navigate("consultas_estudiante")
                        }
                        "inicio_estudiante" -> {
                            // Ya estamos en Inicio
                        }
                        else -> {
                            Toast.makeText(
                                navController.context,
                                "Para una futura actualización",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            )
        }
    ) { paddingValues ->


    Column(
            modifier = Modifier
                .fillMaxSize()
                .background(fondo)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {

            // Encabezado del estudiante
            EncabezadoEstudiante(
                nombre = "Ana Pérez",
                onNotificaciones = {
                    Toast.makeText(
                        navController.context,
                        "Para una futura actualización",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )

            // Contenido principal
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                // Avance del semestre
                TarjetaAvanceSemestre(
                    porcentaje = 58,
                    revisados = 18,
                    pendientes = 13,
                    consultas = 2,
                    semestre = "Semestre 2",
                    asignaturas = 4
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Mis asignaturas
                Text(
                    text = "Mis asignaturas",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = verde
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        TarjetaAsignatura(
                            codigo = "DPE201",
                            nombre = "Derecho Penal I",
                            unidadActual = 3,
                            totalUnidades = 6,
                            progreso = 62,
                            onClick = {
                                navController.navigate("detalle_asignatura/DPE201")
                            }
                        )
                    }

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        TarjetaAsignatura(
                            codigo = "GAP202",
                            nombre = "Gestión Administrativa",
                            unidadActual = 2,
                            totalUnidades = 5,
                            progreso = 40,
                            onClick = {
                                navController.navigate("detalle_asignatura/GAP202")
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Novedades
                Text(
                    text = "Novedades",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = verde
                )

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "Nuevo material disponible",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = verde
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Se ha publicado material de apoyo para Derecho Penal I.",
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Publicación de ejemplo",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
