package com.example.esucar.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.MarkEmailRead
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.esucar.ui.components.PieInstitucional

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCodigoVerificacion(navController: NavController) {
    val context = LocalContext.current
    val verde = Color(0xFF104D30)
    var codigo by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    val codigoSimulado = "123456"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Verificación de Cuenta", color = Color.White) },
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Outlined.MarkEmailRead,
                        contentDescription = null,
                        tint = verde,
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Código de Confirmación",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = verde,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Ingresa el código de 6 dígitos enviado a tu correo institucional para activar tu cuenta.",
                        fontSize = 13.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Chip de simulación de código para facilitar pruebas
                    Surface(
                        color = Color(0xFFE8F5E9),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.clickable {
                            codigo = codigoSimulado
                            mensajeError = ""
                        }
                    ) {
                        Text(
                            text = "💡 Código de prueba simulado: $codigoSimulado (toca aquí para autocompletar)",
                            color = verde,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = codigo,
                        onValueChange = {
                            if (it.length <= 6) {
                                codigo = it
                                mensajeError = ""
                            }
                        },
                        label = { Text("Código (6 dígitos)") },
                        placeholder = { Text("ej. 123456") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = verde,
                            focusedLabelColor = verde
                        )
                    )

                    if (mensajeError.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = mensajeError,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            if (codigo.length == 6) {
                                Toast.makeText(context, "¡Cuenta verificada e ideada con éxito!", Toast.LENGTH_SHORT).show()
                                navController.navigate("login") {
                                    popUpTo("portada") { inclusive = false }
                                }
                            } else {
                                mensajeError = "Ingresa un código válido de 6 dígitos (ej: 123456)"
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = verde)
                    ) {
                        Text(
                            text = "Verificar Código",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    TextButton(
                        onClick = {
                            codigo = codigoSimulado
                            Toast.makeText(context, "Código reenviado a tu correo: $codigoSimulado", Toast.LENGTH_LONG).show()
                        }
                    ) {
                        Text(
                            text = "¿No recibiste el código? Reenviar",
                            color = verde,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}
