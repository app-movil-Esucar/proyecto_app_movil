package com.example.esucar.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
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
import com.example.esucar.ui.components.BarraNavegacionEstudiante

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaConversacionConsulta(
    navController: NavController,
    consultaId: String = "1",
    perfilUsuario: String = "Estudiante"
) {
    val context = LocalContext.current
    val databaseHelper = remember(context) { DatabaseHelper(context.applicationContext) }

    val idInt = consultaId.toIntOrNull() ?: 1
    var consultaBD by remember(consultaId) { mutableStateOf(databaseHelper.obtenerConsultaPorId(idInt)) }
    var mensajesBD by remember(consultaId) { mutableStateOf(databaseHelper.obtenerMensajesDeConsulta(idInt)) }

    val verde = Color(0xFF104D30)
    var nuevoMensaje by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Consulta #$consultaId", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = "Asignatura: ${consultaBD?.codigoAsignatura ?: "DPE201"} • $perfilUsuario",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 12.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    val estaRespondida = consultaBD?.estado == "Respondida" || !consultaBD?.respuesta.isNullOrEmpty()
                    Surface(
                        color = if (estaRespondida) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text(
                            text = if (estaRespondida) "Respondida" else "Pendiente",
                            color = if (estaRespondida) verde else Color(0xFFC62828),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = verde)
            )
        },
        bottomBar = {
            if (perfilUsuario == "Docente") {
                BarraNavegacionDocente(
                    seleccionada = "consultas_docente",
                    onSeleccionar = { ruta -> navController.navigate(ruta) }
                )
            } else {
                BarraNavegacionEstudiante(
                    seleccionada = "consultas_estudiante",
                    onSeleccionar = { ruta -> navController.navigate(ruta) }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF2F4EF))
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(mensajesBD) { msg ->
                    // ALINEACIÓN SEGÚN PERFIL ACTUAL:
                    // Si el remitente del mensaje es el perfil con el que inicié sesión -> ES MI MENSAJE (Derecha, Verde)
                    // Si el mensaje es del otro rol -> ES EL OTRO USUARIO (Izquierda, Blanco)
                    val esMiMensaje = msg.perfilRemitente == perfilUsuario
                    val alinearAlineacion = if (esMiMensaje) Alignment.End else Alignment.Start
                    val colorFondoGlobo = if (esMiMensaje) verde else Color.White
                    val colorTexto = if (esMiMensaje) Color.White else Color.DarkGray

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = alinearAlineacion
                    ) {
                        Text(
                            text = if (esMiMensaje) "${msg.remitenteNombre} (Tú)" else msg.remitenteNombre,
                            fontSize = 11.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )

                        Card(
                            shape = RoundedCornerShape(
                                topStart = 12.dp,
                                topEnd = 12.dp,
                                bottomStart = if (esMiMensaje) 12.dp else 2.dp,
                                bottomEnd = if (esMiMensaje) 2.dp else 12.dp
                            ),
                            colors = CardDefaults.cardColors(containerColor = colorFondoGlobo),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.widthIn(max = 290.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = msg.mensaje,
                                    fontSize = 14.sp,
                                    color = colorTexto
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = msg.fecha,
                                    fontSize = 10.sp,
                                    color = if (esMiMensaje) Color.White.copy(alpha = 0.7f) else Color.Gray,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        }
                    }
                }
            }

            // Input box para enviar una nueva respuesta o mensaje en la conversación
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = nuevoMensaje,
                    onValueChange = { nuevoMensaje = it },
                    placeholder = { Text("Escribe un mensaje...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = verde,
                        unfocusedBorderColor = Color.LightGray
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        val texto = nuevoMensaje.trim()
                        if (texto.isNotEmpty()) {
                            val nombreRemitente = if (perfilUsuario == "Docente") "Prof. Juan Ramírez" else "Carabinero Alumno"
                            val exito = databaseHelper.agregarMensajeConsulta(
                                consultaId = idInt,
                                remitenteNombre = nombreRemitente,
                                perfilRemitente = perfilUsuario,
                                mensaje = texto
                            )
                            if (exito) {
                                mensajesBD = databaseHelper.obtenerMensajesDeConsulta(idInt)
                                consultaBD = databaseHelper.obtenerConsultaPorId(idInt)
                                nuevoMensaje = ""
                            } else {
                                Toast.makeText(context, "Error al enviar mensaje", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .background(verde, shape = RoundedCornerShape(20.dp))
                        .size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Enviar",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
