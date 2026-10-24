package com.example.esucar.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.esucar.ui.components.BarraNavegacionEstudiante
import com.example.esucar.model.Asignatura
import com.example.esucar.ui.components.TarjetaAsignatura
import android.widget.Toast

@Composable
fun PantallaMisAsignaturas(navController: NavController) {
    val fondo = Color(0xFFF1F3F0)
    val verde = Color(0xFF104D30)
    var busqueda by remember { mutableStateOf("") }
    var semestreSeleccionado by remember { mutableIntStateOf(2) }

    val asignaturas = listOf(
        Asignatura(
            codigo = "DPE201",
            nombre = "Derecho Penal I",
            area = "Área Jurídica",
            docente = "Docente de ejemplo",
            semestre = 2,
            totalUnidades = 6,
            unidadesCompletadas = 3,
            progreso = 62
        ),
        Asignatura(
            codigo = "GAP202",
            nombre = "Gestión Administrativa",
            area = "Área Administrativa",
            docente = "Docente de ejemplo",
            semestre = 2,
            totalUnidades = 5,
            unidadesCompletadas = 2,
            progreso = 40
        ),
        Asignatura(
            codigo = "DOI203",
            nombre = "Doctrina Institucional",
            area = "Formación Institucional",
            docente = "Docente de ejemplo",
            semestre = 2,
            totalUnidades = 5,
            unidadesCompletadas = 3,
            progreso = 60
        )
    )

    val asignaturasFiltradas = asignaturas.filter { asignatura ->
        asignatura.semestre == semestreSeleccionado && (asignatura.nombre.contains(busqueda.trim(),
            ignoreCase = true) || asignatura.codigo.contains(busqueda.trim(), ignoreCase = true))
    }

    Scaffold(
        bottomBar = {
            BarraNavegacionEstudiante(
                seleccionada = "mis_asignaturas",
                onSeleccionar = { ruta ->
                    when (ruta) {
                        "inicio_estudiante" -> {
                            navController.navigate("inicio_estudiante")
                        }

                        "consultas_estudiante" -> {
                            navController.navigate("consultas_estudiante")
                        }

                        "mis_asignaturas" -> {
                            // Ya estamos en esta pantalla
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
                .padding(16.dp)
        ) {
            Text(
                text = "Mis asignaturas",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF172B20)
            )
            Text(
                text = "Semestre $semestreSeleccionado · ${asignaturasFiltradas.size} asignaturas",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Buscar asignatura")
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                for (semestre in 1..4) {
                    Button(
                        onClick = {
                            semestreSeleccionado = semestre
                        },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(
                            horizontal = 2.dp,
                            vertical = 8.dp
                        ),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                                if (semestreSeleccionado == semestre)
                                    verde
                                else
                                    Color.White,
                            contentColor =
                                if (semestreSeleccionado == semestre)
                                    Color.White
                                else
                                    verde
                        )
                    ) {
                        Text(
                            text = "Sem.$semestre",
                            fontSize = 11.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (asignaturasFiltradas.isEmpty()) {
                Text(
                    text = "No se encontraron asignaturas",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            } else {
                asignaturasFiltradas.forEach { asignatura ->
                    TarjetaAsignatura(
                        codigo = asignatura.codigo,
                        nombre = asignatura.nombre,
                        unidadActual = asignatura.unidadesCompletadas,
                        totalUnidades = asignatura.totalUnidades,
                        progreso = asignatura.progreso,
                        onClick = {
                            navController.navigate(
                                "detalle_asignatura/${asignatura.codigo}"
                            )
                        }

                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

        }
    }
}

