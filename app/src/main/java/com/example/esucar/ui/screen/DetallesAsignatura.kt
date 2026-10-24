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
import com.example.esucar.model.MaterialEstudio

@Composable
fun PantallaDetalleAsignatura(
    navController: NavController,
    codigo: String
) {
    val verde = Color(0xFF104D30)
    val fondo = Color(0xFFF1F3F0)
    val context = LocalContext.current

    val nombreAsignatura = when (codigo) {
        "DPE201" -> "Derecho Penal I"
        "GAP202" -> "Gestión Administrativa"
        "DOI203" -> "Doctrina Institucional"
        else -> "Asignatura no encontrada"
    }

    val progresoAsignatura = when (codigo) {
        "DPE201" -> 62
        "GAP202" -> 40
        "DOI203" -> 60
        else -> 0
    }

    Scaffold(
        containerColor = fondo,
        bottomBar = {
            BotonesDetalleAsignatura(
                onDescargar = {
                    Toast.makeText(
                        context,
                        "Para una futura actualización",
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
                        text = "$codigo · Docente de ejemplo",
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

                when (codigo) {

                    // DERECHO PENAL I
                    "DPE201" -> {
                        TarjetaUnidad(
                            numero = 1,
                            titulo = "Teoría del delito",
                            avance = "5/5",
                            contenido = listOf(
                                MaterialEstudio("Introducción a la teoría del delito", "Revisado"),
                                MaterialEstudio("Elementos del delito", "Revisado"),
                                MaterialEstudio("Material de repaso", "Revisado")
                            ),
                            onMaterialClick = { nombreMaterial ->
                                navController.navigate(
                                    "detalle_material/${Uri.encode(nombreMaterial)}?codigo=$codigo&unidad=1"
                                )
                            }
                        )

                        TarjetaUnidad(
                            numero = 2,
                            titulo = "Delitos contra las personas",
                            avance = "6/6",
                            contenido = listOf(
                                MaterialEstudio("Conceptos principales", "Revisado"),
                                MaterialEstudio("Material de lectura", "Revisado"),
                                MaterialEstudio("Ejercicios de repaso", "Revisado")
                            ),
                            onMaterialClick = { nombreMaterial ->
                                navController.navigate(
                                    "detalle_material/${Uri.encode(nombreMaterial)}?codigo=$codigo&unidad=2"
                                )
                            }
                        )

                        TarjetaUnidad(
                            numero = 3,
                            titulo = "Delitos contra la propiedad",
                            avance = "4/7",
                            contenido = listOf(
                                MaterialEstudio("Código Penal, Libro II", "Revisado"),
                                MaterialEstudio("Clase 3: Hurto y robo", "Revisado"),
                                MaterialEstudio("Guía de ejercicios N°3", "En curso"),
                                MaterialEstudio("Análisis de casos prácticos", "Pendiente")
                            ),
                            onMaterialClick = { nombreMaterial ->
                                navController.navigate(
                                    "detalle_material/${Uri.encode(nombreMaterial)}?codigo=$codigo&unidad=3"
                                )
                            }
                        )

                        TarjetaUnidad(
                            numero = 4,
                            titulo = "Delitos funcionarios",
                            avance = "0/6",
                            contenido = listOf(
                                MaterialEstudio(
                                    "Material pendiente de publicación",
                                    "Pendiente"
                                )
                            ),
                            onMaterialClick = { nombreMaterial ->
                                navController.navigate(
                                    "detalle_material/${Uri.encode(nombreMaterial)}?codigo=$codigo&unidad=4"
                                )
                            }
                        )
                    }

                    // GESTIÓN ADMINISTRATIVA
                    "GAP202" -> {
                        TarjetaUnidad(
                            numero = 1,
                            titulo = "Introducción a la gestión administrativa",
                            avance = "1/2",
                            contenido = listOf(
                                MaterialEstudio(
                                    "Conceptos básicos de administración",
                                    "Revisado"
                                ),
                                MaterialEstudio(
                                    "Organización administrativa",
                                    "Pendiente"
                                )
                            ),
                            onMaterialClick = { nombreMaterial ->
                                navController.navigate(
                                    "detalle_material/${Uri.encode(nombreMaterial)}?codigo=$codigo&unidad=2"
                                )
                            }
                        )

                        TarjetaUnidad(
                            numero = 2,
                            titulo = "Procesos administrativos",
                            avance = "0/2",
                            contenido = listOf(
                                MaterialEstudio(
                                    "Planificación y organización",
                                    "Pendiente"
                                ),
                                MaterialEstudio(
                                    "Control de procesos",
                                    "Pendiente"
                                )
                            ),
                            onMaterialClick = { nombreMaterial ->
                                navController.navigate(
                                    "detalle_material/${Uri.encode(nombreMaterial)}?codigo=$codigo&unidad=2"
                                )
                            }
                        )
                    }

                    // DOCTRINA INSTITUCIONAL
                    "DOI203" -> {
                        TarjetaUnidad(
                            numero = 1,
                            titulo = "Fundamentos institucionales",
                            avance = "1/2",
                            contenido = listOf(
                                MaterialEstudio(
                                    "Historia institucional",
                                    "Revisado"
                                ),
                                MaterialEstudio(
                                    "Principios institucionales",
                                    "Pendiente"
                                )
                            ),
                            onMaterialClick = { nombreMaterial ->
                                navController.navigate(
                                    "detalle_material/${Uri.encode(nombreMaterial)}?codigo=$codigo&unidad=1"
                                )
                            }
                        )
                        TarjetaUnidad(
                            numero = 2,
                            titulo = "Valores y deberes",
                            avance = "0/2",
                            contenido = listOf(
                                MaterialEstudio(
                                    "Valores institucionales",
                                    "Pendiente"
                                ),
                                MaterialEstudio(
                                    "Deberes profesionales",
                                    "Pendiente"
                                )
                            ),
                            onMaterialClick = { nombreMaterial ->
                                navController.navigate(
                                    "detalle_material/${Uri.encode(nombreMaterial)}?codigo=$codigo&unidad=2"
                                )
                            }
                        )
                    }

                    else -> {
                        Text(
                            text = "No hay unidades disponibles para esta asignatura."
                        )
                    }
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
    var expandida by remember { mutableStateOf(false) }
    val verde = Color(0xFF104D30)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expandida = !expandida
                    }
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = numero.toString(),
                    color = verde,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 12.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
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
                    imageVector = if (expandida) {
                        Icons.Outlined.ExpandLess
                    } else {
                        Icons.Outlined.ExpandMore
                    },
                    contentDescription = if (expandida) {
                        "Contraer unidad"
                    } else {
                        "Expandir unidad"
                    },
                    tint = verde
                )
            }

            if (expandida) {
                HorizontalDivider()

                contenido.forEach { material ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onMaterialClick(material.nombre)
                            }
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
                                "Revisado" -> Color(0xFF104D30)
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
            colors = ButtonDefaults.buttonColors(
                containerColor = verde
            )
        ) {
            Text(
                text = "Consultar",
                fontSize = 12.sp
            )
        }
    }
}

