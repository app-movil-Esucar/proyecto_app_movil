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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.esucar.database.DatabaseHelper
import com.example.esucar.ui.components.BarraNavegacionEstudiante
import com.example.esucar.ui.components.TarjetaAsignatura

@Composable
fun PantallaMisAsignaturas(navController: NavController) {
    val context = LocalContext.current
    val databaseHelper = remember(context) { DatabaseHelper(context.applicationContext) }

    val fondo = Color(0xFFF1F3F0)
    val verde = Color(0xFF104D30)
    var busqueda by remember { mutableStateOf("") }
    var semestreSeleccionado by remember { mutableIntStateOf(1) }

    val asignaturas = remember { databaseHelper.obtenerAsignaturas() }

    val asignaturasFiltradas = asignaturas.filter { asignatura ->
        (asignatura.semestre == semestreSeleccionado || semestreSeleccionado == 0) &&
                (asignatura.nombre.contains(busqueda.trim(), ignoreCase = true) ||
                        asignatura.codigo.contains(busqueda.trim(), ignoreCase = true))
    }

    Scaffold(
        bottomBar = {
            BarraNavegacionEstudiante(
                seleccionada = "mis_asignaturas",
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
                .padding(16.dp)
        ) {
            Text(
                text = "Mis Asignaturas",
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
                placeholder = { Text("Buscar asignatura") },
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
                        onClick = { semestreSeleccionado = semestre },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (semestreSeleccionado == semestre) verde else Color.White,
                            contentColor = if (semestreSeleccionado == semestre) Color.White else verde
                        )
                    ) {
                        Text(text = "Sem.$semestre", fontSize = 11.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (asignaturasFiltradas.isEmpty()) {
                Text(
                    text = "No se encontraron asignaturas para el Semestre $semestreSeleccionado",
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
                            navController.navigate("detalle_asignatura/${asignatura.codigo}")
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}
