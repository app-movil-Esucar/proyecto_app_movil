package com.example.esucar.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.FileUpload
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
import com.example.esucar.ui.components.PieInstitucional

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPublicarMaterial(navController: NavController) {
    val context = LocalContext.current
    val databaseHelper = remember(context) { DatabaseHelper(context.applicationContext) }

    val verde = Color(0xFF104D30)

    var asignaturaSeleccionada by remember { mutableStateOf("Derecho Penal I") }
    var unidadSeleccionada by remember { mutableStateOf("Unidad 1") }
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var nombreArchivo by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    var expandidoAsignatura by remember { mutableStateOf(false) }
    var expandidoUnidad by remember { mutableStateOf(false) }

    val asignaturas = listOf("Derecho Penal I", "Procedimientos Policiales", "Derechos Humanos")
    val unidades = listOf("Unidad 1", "Unidad 2", "Unidad 3", "Unidad 4")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Publicar Nuevo Material", color = Color.White) },
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
        bottomBar = { PieInstitucional() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF2F4EF))
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Información del Recurso",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = verde,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Dropdown Asignatura
                    ExposedDropdownMenuBox(
                        expanded = expandidoAsignatura,
                        onExpandedChange = { expandidoAsignatura = !expandidoAsignatura }
                    ) {
                        OutlinedTextField(
                            value = asignaturaSeleccionada,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Asignatura") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoAsignatura) },
                            modifier = Modifier
                                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                                .fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = expandidoAsignatura,
                            onDismissRequest = { expandidoAsignatura = false }
                        ) {
                            asignaturas.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(item) },
                                    onClick = {
                                        asignaturaSeleccionada = item
                                        expandidoAsignatura = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Dropdown Unidad
                    ExposedDropdownMenuBox(
                        expanded = expandidoUnidad,
                        onExpandedChange = { expandidoUnidad = !expandidoUnidad }
                    ) {
                        OutlinedTextField(
                            value = unidadSeleccionada,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Unidad") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoUnidad) },
                            modifier = Modifier
                                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                                .fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = expandidoUnidad,
                            onDismissRequest = { expandidoUnidad = false }
                        ) {
                            unidades.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(item) },
                                    onClick = {
                                        unidadSeleccionada = item
                                        expandidoUnidad = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = titulo,
                        onValueChange = { titulo = it },
                        label = { Text("Título del Material") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = verde, focusedLabelColor = verde)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripción (opcional)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = verde, focusedLabelColor = verde)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Attach File Selector Simulator
                    OutlinedButton(
                        onClick = {
                            nombreArchivo = "Documento_Estudio_DP1.pdf"
                            Toast.makeText(context, "Archivo adjuntado correctamente", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(imageVector = Icons.Outlined.FileUpload, contentDescription = null, tint = verde)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (nombreArchivo.isEmpty()) "Adjuntar Archivo (PDF, MP4, DOC)" else "Adjuntado: $nombreArchivo",
                            color = verde,
                            fontSize = 13.sp
                        )
                    }

                    if (mensajeError.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = mensajeError, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (titulo.isNotBlank()) {
                                val codigoMap = when (asignaturaSeleccionada) {
                                    "Derecho Penal I" -> "DER-101"
                                    "Procedimientos Policiales" -> "PRO-201"
                                    else -> "DDHH-102"
                                }
                                val exito = databaseHelper.insertarMaterial(
                                    codigoAsignatura = codigoMap,
                                    unidad = unidadSeleccionada,
                                    nombre = titulo,
                                    tipo = "PDF"
                                )
                                if (exito) {
                                    Toast.makeText(context, "¡Material publicado exitosamente!", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                } else {
                                    Toast.makeText(context, "Error al guardar el material", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                mensajeError = "Ingresa el título del material para continuar"
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = verde)
                    ) {
                        Text("Publicar Material", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
