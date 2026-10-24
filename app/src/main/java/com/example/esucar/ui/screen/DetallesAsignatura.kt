package com.example.esucar.ui.screen

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
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
import com.example.esucar.model.MaterialEstudio

@Composable
fun PantallaDetalleAsignatura(
    navController: NavController,
    codigo: String
) {
    val verde = Color(0xFF104D30)
    val fondo = Color(0xFFF1F3F0)
    val context = LocalContext.current
    val databaseHelper = remember(context) { DatabaseHelper(context.applicationContext) }

    // Consultar asignatura y materiales directamente desde SQLite
    val asignaturaBD = remember(codigo) { databaseHelper.obtenerAsignaturaPorCodigo(codigo) }
    val materialesBD = remember(codigo) { databaseHelper.obtenerMaterialesPorAsignatura(codigo) }

    val nombreAsignatura = asignaturaBD?.nombre ?: when (codigo) {
        "DPE201" -> "Derecho Penal I"
        "GAP202" -> "Gestión Administrativa"
        "DOI203" -> "Doctrina Institucional"
        else -> "Asignatura ($codigo)"
    }

    val docenteAsignatura = asignaturaBD?.docente ?: "Prof. Juan Ramírez"
    val progresoAsignatura = asignaturaBD?.progreso ?: 62

    // Agrupar materiales por Unidad dinámicamente desde SQLite
    val materialesPorUnidad = remember(materialesBD) {
        if (materialesBD.isNotEmpty()) {
            materialesBD.groupBy { it.unidad }
        } else {
            mapOf(
                "Unidad 1" to listOf(
                    MaterialEstudio("Introducción a la asignatura", "Revisado", codigoAsignatura = codigo, unidad = "Unidad 1"),
                    MaterialEstudio("Conceptos fundamentales", "Revisado", codigoAsignatura = codigo, unidad = "Unidad 1")
                ),
                "Unidad 2" to listOf(
                    MaterialEstudio("Marco legal y reglamentos", "Pendiente", codigoAsignatura = codigo, unidad = "Unidad 2"),
                    MaterialEstudio("Casos de estudio prácticos", "Pendiente", codigoAsignatura = codigo, unidad = "Unidad 2")
                )
            )
        }
    }

    Scaffold(
        containerColor = fondo,
        bottomBar = {
            BotonesDetalleAsignatura(
                onDescargar = {
                    Toast.makeText(
                        context,
                        "Descargando unidad desde SQLite...",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onConsultar = {
                    navController.navigate("nueva_consulta?codigo=$codigo")
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Encabezado
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        navController.navigate("mis_asignaturas")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Volver",
                        tint = verde
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = nombreAsignatura,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF172B20)
                    )

                    Text(
                        text = "$codigo · $docenteAsignatura",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }

            // Barra de progreso
            LinearProgressIndicator(
                progress = { progresoAsignatura / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp),
                color = verde,
                trackColor = Color(0xFFE1E6E2)
            )

            // Contenido desplazable
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(
                    text = "Unidades de la asignatura",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = verde
                )

                Spacer(modifier = Modifier.height(12.dp))

                var indexUnidad = 1
                materialesPorUnidad.forEach { (nombreUnidad, listaMat) ->
                    val leidos = listaMat.count { it.estado == "Leído" || it.estado == "Revisado" }
                    val total = listaMat.size

                    TarjetaUnidad(
                        numero = indexUnidad,
                        titulo = nombreUnidad,
                        avance = "$leidos/$total",
                        contenido = listaMat,
                        onMaterialClick = { nombreMaterial ->
                            navController.navigate(
                                "detalle_material/${Uri.encode(nombreMaterial)}?codigo=$codigo&unidad=$indexUnidad"
                            )
                        }
                    )
                    indexUnidad++
                }
            }
        }
    }
}

// Tarjeta desplegable de cada unidad
@Composable
private fun TarjetaUnidad(
    numero: Int,
    titulo: String,
    avance: String,
    contenido: List<MaterialEstudio>,
    onMaterialClick: (String) -> Unit
) {
    var expandida by remember { mutableStateOf(true) }
    val verde = Color(0xFF104D30)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandida = !expandida }
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = numero.toString(),
                    color = verde,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 12.dp)
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Unidad $numero",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = titulo,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = avance,
                    fontSize = 12.sp,
                    color = verde
                )

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = if (expandida) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                    contentDescription = if (expandida) "Contraer unidad" else "Expandir unidad",
                    tint = verde
                )
            }

            if (expandida) {
                HorizontalDivider()

                contenido.forEach { material ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onMaterialClick(material.nombre) }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = material.nombre,
                            fontSize = 12.sp,
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = material.estado,
                            fontSize = 11.sp,
                            color = when (material.estado) {
                                "Revisado", "Leído" -> Color(0xFF104D30)
                                "En curso" -> Color(0xFFE85D18)
                                else -> Color.Gray
                            }
                        )
                    }
                }
            }
        }
    }
}

// Botones fijos en la parte inferior
@Composable
private fun BotonesDetalleAsignatura(
    onDescargar: () -> Unit,
    onConsultar: () -> Unit
) {
    val verde = Color(0xFF104D30)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(
            onClick = onDescargar,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(10.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Download,
                contentDescription = null,
                tint = verde,
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "Descargar unidad",
                fontSize = 11.sp,
                color = verde
            )
        }

        Button(
            onClick = onConsultar,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = verde)
        ) {
            Text(
                text = "Consultar",
                fontSize = 12.sp
            )
        }
    }
}
