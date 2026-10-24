package com.example.esucar.ui.screen

import android.widget.Toast
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
import com.example.esucar.ui.components.EncabezadoEstudiante
import com.example.esucar.ui.components.TarjetaAsignatura
import com.example.esucar.ui.components.TarjetaAvanceSemestre

@Composable
fun PantallaInicioEstudiante(navController: NavController) {
    val context = LocalContext.current
    val databaseHelper = remember(context) { DatabaseHelper(context.applicationContext) }

    val fondo = Color(0xFFF1F3F0)
    val verde = Color(0xFF104D30)

    val asignaturasBD = remember { databaseHelper.obtenerAsignaturas() }

    Scaffold(
        bottomBar = {
            BarraNavegacionEstudiante(
                seleccionada = "inicio_estudiante",
                onSeleccionar = { ruta -> navController.navigate(ruta) }
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
            // Encabezado del estudiante con botón Cerrar Sesión
            EncabezadoEstudiante(
                nombre = "Carabinero Alumno",
                onNotificaciones = { navController.navigate("notificaciones") },
                onCerrarSesion = {
                    Toast.makeText(context, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show()
                    navController.navigate("acceso") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Avance del semestre
                TarjetaAvanceSemestre(
                    porcentaje = 62,
                    revisados = 18,
                    pendientes = 13,
                    consultas = 2,
                    semestre = "Semestre 1",
                    asignaturas = asignaturasBD.size
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Mis Asignaturas",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = verde
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (asignaturasBD.isEmpty()) {
                    Text("Cargando asignaturas...", color = Color.Gray, fontSize = 14.sp)
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        asignaturasBD.take(2).forEach { asig ->
                            Box(modifier = Modifier.weight(1f)) {
                                TarjetaAsignatura(
                                    codigo = asig.codigo,
                                    nombre = asig.nombre,
                                    unidadActual = asig.unidadesCompletadas,
                                    totalUnidades = asig.totalUnidades,
                                    progreso = asig.progreso,
                                    onClick = {
                                        navController.navigate("detalle_asignatura/${asig.codigo}")
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Novedades
                Text(
                    text = "Novedades & Avisos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = verde
                )

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    onClick = { navController.navigate("notificaciones") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Nuevo material de estudio disponible",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = verde
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Se ha publicado la guía de ejercicios de Derecho Penal I.",
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Toca para ver todas las notificaciones →",
                            fontSize = 11.sp,
                            color = Color(0xFFE5B000),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
