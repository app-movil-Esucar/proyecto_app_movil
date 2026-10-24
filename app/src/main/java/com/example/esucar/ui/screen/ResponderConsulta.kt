package com.example.esucar.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
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
fun PantallaResponderConsulta(
    navController: NavController,
    consultaId: String = "1"
) {
    val context = LocalContext.current
    val databaseHelper = remember(context) { DatabaseHelper(context.applicationContext) }

    val verde = Color(0xFF104D30)
    var respuestaTexto by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    val idInt = consultaId.toIntOrNull() ?: 1

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Responder Consulta #$consultaId", color = Color.White) },
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
            // Original Question Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Consulta de Carabinero Alumno",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = verde
                    )
                    Text(
                        text = "Asignatura: Derecho Penal I • Consulta #$consultaId",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "\"Profesor, quisiera saber cómo se aplica la agravante de reincidencia en faltas menores según la última jurisprudencia.\"",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Redactar Respuesta Oficial",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = verde,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = respuestaTexto,
                        onValueChange = { respuestaTexto = it },
                        label = { Text("Escribe tu respuesta clara y fundamentada...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = verde,
                            focusedLabelColor = verde
                        )
                    )

                    if (mensajeError.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = mensajeError, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (respuestaTexto.trim().length >= 5) {
                                val exito = databaseHelper.responderConsulta(idInt, respuestaTexto.trim())
                                if (exito) {
                                    Toast.makeText(context, "Respuesta enviada exitosamente", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                } else {
                                    Toast.makeText(context, "Respuesta enviada", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                }
                            } else {
                                mensajeError = "La respuesta debe contener al menos 5 caracteres."
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = verde)
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Enviar Respuesta", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                }
            }
        }
    }
}
