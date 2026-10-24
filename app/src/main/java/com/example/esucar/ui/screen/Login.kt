package com.example.esucar.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.esucar.database.DatabaseHelper
import com.example.esucar.ui.components.EncabezadoEsucar
import com.example.esucar.ui.components.PieInstitucional
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PantallaLogin(navController: NavController) {
    var usuarioInstitucional by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    var mostrarDialogoRecuperar by remember { mutableStateOf(false) }
    var correoRecuperar by remember { mutableStateOf("") }

    val context = LocalContext.current
    val databaseHelper = remember(context) { DatabaseHelper(context.applicationContext) }
    val scope = rememberCoroutineScope()

    var iniciandoSesion by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    val verde = Color(0xFF104D30)
    val fondo = Color(0xFFF1F3F0)

    // Cuadro de Diálogo Simulado de Recuperación de Contraseña
    if (mostrarDialogoRecuperar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoRecuperar = false },
            title = {
                Text(
                    text = "Recuperar Contraseña",
                    fontWeight = FontWeight.Bold,
                    color = verde,
                    fontSize = 18.sp
                )
            },
            text = {
                Column {
                    Text(
                        text = "Ingresa tu correo institucional registrado para enviarte las instrucciones y el código de verificación simulado.",
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = correoRecuperar,
                        onValueChange = { correoRecuperar = it },
                        label = { Text("Correo Institucional") },
                        placeholder = { Text("ej. estudiante@esucar.cl") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (correoRecuperar.isNotBlank()) {
                            mostrarDialogoRecuperar = false
                            Toast.makeText(
                                context,
                                "Código enviado a ${correoRecuperar.trim()}. Usa el código simulado 123456",
                                Toast.LENGTH_LONG
                            ).show()
                            navController.navigate("codigo_verificacion")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = verde)
                ) {
                    Text("Enviar Código")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { mostrarDialogoRecuperar = false }
                ) {
                    Text("Cancelar", color = Color.Gray)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .safeDrawingPadding()
    ) {
        // Encabezado institucional
        EncabezadoEsucar()

        // Formulario
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Iniciar sesión",
                color = Color(0xFF172B20),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Indicador de perfil automático por credenciales
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = verde.copy(alpha = 0.08f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Badge,
                        contentDescription = null,
                        tint = verde,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Acceso Único • Estudiante / Docente",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = verde
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Campo Correo Institucional
            Text(
                text = "Correo institucional",
                color = Color(0xFF45564B),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = usuarioInstitucional,
                onValueChange = { usuarioInstitucional = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("ej. estudiante@esucar.cl o docente@esucar.cl") },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = verde,
                    unfocusedBorderColor = Color(0xFFD5DDD5)
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Campo Contraseña
            Text(
                text = "Contraseña",
                color = Color(0xFF45564B),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = verde,
                    unfocusedBorderColor = Color(0xFFD5DDD5)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Botón Recuperar contraseña simulado
            TextButton(
                onClick = {
                    correoRecuperar = usuarioInstitucional
                    mostrarDialogoRecuperar = true
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "¿Olvidaste tu contraseña?",
                    fontSize = 13.sp,
                    color = verde,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Botón Ingresar
            Button(
                onClick = {
                    mensajeError = ""
                    if (usuarioInstitucional.isBlank() || contrasena.isBlank()) {
                        mensajeError = "Debes ingresar tu correo y contraseña"
                    } else if (!iniciandoSesion) {
                        iniciandoSesion = true
                        val correoIngresado = usuarioInstitucional.trim()
                        val contrasenaIngresada = contrasena
                        scope.launch {
                            try {
                                val usuario = withContext(Dispatchers.IO) {
                                    databaseHelper.iniciarSesion(
                                        correo = correoIngresado,
                                        contrasena = contrasenaIngresada
                                    )
                                }
                                if (usuario != null) {
                                    mensajeError = ""
                                    when (usuario.perfil) {
                                        "Estudiante" -> {
                                            navController.navigate("inicio_estudiante") {
                                                popUpTo("portada") { inclusive = false }
                                            }
                                        }
                                        "Docente" -> {
                                            navController.navigate("inicio_docente") {
                                                popUpTo("portada") { inclusive = false }
                                            }
                                        }
                                        else -> {
                                            navController.navigate("inicio_estudiante")
                                        }
                                    }
                                } else {
                                    mensajeError = "Correo o contraseña incorrectos"
                                }
                            } catch (e: Exception) {
                                android.util.Log.e("ESUCAR_LOGIN", "Error al iniciar sesión", e)
                                mensajeError = "No se pudo iniciar sesión"
                            } finally {
                                iniciandoSesion = false
                            }
                        }
                    }
                },
                enabled = !iniciandoSesion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = verde,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (iniciandoSesion) "Verificando..." else "Ingresar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pie institucional
            PieInstitucional()
        }
    }
}
