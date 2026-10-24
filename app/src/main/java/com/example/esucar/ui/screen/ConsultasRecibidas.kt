package com.example.esucar.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.esucar.database.DatabaseHelper
import com.example.esucar.ui.components.BarraNavegacionDocente

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaConsultasRecibidas(navController: NavController) {
    val context = LocalContext.current
    val databaseHelper = remember(context) { DatabaseHelper(context.applicationContext) }

    val verde = Color(0xFF104D30)
    var filtroSeleccionado by remember { mutableStateOf("Pendientes") }

    var consultas by remember { mutableStateOf(databaseHelper.obtenerConsultas()) }

    val consultasFiltradas = when (filtroSeleccionado) {
        "Pendientes" -> consultas.filter { it.estado == "Pendiente" }
        "Respondidas" -> consultas.filter { it.estado == "Respondida" }
        else -> consultas
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Consultas de Alumnos", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = verde)
            )
        },
        bottomBar = {
            BarraNavegacionDocente(
                seleccionada = "consultas_docente",
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
            // Filter Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Pendientes", "Respondidas", "Todas").forEach { tab ->
                    FilterChip(
                        selected = filtroSeleccionado == tab,
                        onClick = { filtroSeleccionado = tab },
                        label = { Text(tab) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = verde,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            if (consultasFiltradas.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay consultas en esta categoría", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(consultasFiltradas) { item ->
                        Card(
                            onClick = {
                                if (item.estado == "Respondida") {
                                    navController.navigate("conversacion/${item.id}?perfil=Docente")
                                } else {
                                    navController.navigate("responder_consulta/${item.id}")
                                }
                            },
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
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.ChatBubbleOutline,
                                            contentDescription = null,
                                            tint = verde,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = item.estudianteNombre,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = verde
                                        )
                                    }

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Surface(
                                            color = if (item.estado == "Respondida") Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Text(
                                                text = if (item.estado == "Respondida") "Respondida" else "Pendiente",
                                                color = if (item.estado == "Respondida") verde else Color(0xFFC62828),
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }

                                        IconButton(
                                            onClick = {
                                                val exito = databaseHelper.eliminarConsulta(item.id)
                                                if (exito) {
                                                    consultas = databaseHelper.obtenerConsultas()
                                                    Toast.makeText(context, "Consulta eliminada de la base de datos", Toast.LENGTH_SHORT).show()
                                                }
                                            },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Outlined.Delete,
                                                contentDescription = "Eliminar consulta",
                                                tint = Color.Gray,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "Asignatura: ${item.codigoAsignatura}",
                                    fontSize = 12.sp,
                                    color = Color(0xFFE5B000),
                                    fontWeight = FontWeight.SemiBold
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = item.pregunta,
                                    fontSize = 13.sp,
                                    color = Color.DarkGray
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = item.fecha,
                                        fontSize = 11.sp,
                                        color = Color.Gray
                                    )

                                    Text(
                                        text = if (item.estado == "Respondida") "Ver conversación →" else "Responder →",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = verde
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
