package com.example.esucar.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.esucar.database.DatabaseHelper
import com.example.esucar.ui.components.EncabezadoEsucar
import com.example.esucar.ui.components.PieInstitucional
import com.example.esucar.ui.components.SelectorPerfil
import com.example.esucar.validation.ErroresRegistro
import com.example.esucar.validation.validarRegistro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PantallaRegistro(navController: NavController? = null) {
    var tipoUsuario by remember { mutableStateOf("Estudiante") }
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }

    val context = LocalContext.current
    val databaseHelper = remember(context) { DatabaseHelper(context.applicationContext) }

    var errores by remember { mutableStateOf(ErroresRegistro()) }
    var intentoRegistro by remember { mutableStateOf(false) }

    fun actualizarErrores() {
        if (intentoRegistro) {
            errores = validarRegistro(
                nombre = nombre,
                correo = correo,
                contrasena = contrasena,
                confirmarContrasena = confirmarContrasena
            )
        }
    }

    val scope = rememberCoroutineScope()
    var guardando by remember { mutableStateOf(false) }
    var mensajeExito by remember { mutableStateOf("") }

    val verde = Color(0xFF104D30)
    val fondo = Color(0xFFF1F3F0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
    ) {
        // Encabezado
        EncabezadoEsucar()

        // Formulario
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Encabezado con Botón Volver Atrás
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController?.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver Atrás",
                        tint = verde
                    )
                }

                Text(
                    text = "Registrarse",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF172B20),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                // Contrapeso visual para centrar el título
                Spacer(modifier = Modifier.width(48.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Selector de perfil
            SelectorPerfil(
                tipoUsuario = tipoUsuario,
                onSeleccionar = { tipoUsuario = it }
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Nombre completo
            Text("Nombre completo", fontSize = 12.sp, color = verde)
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    actualizarErrores()
                },
                isError = intentoRegistro && errores.nombre.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            if (intentoRegistro && errores.nombre.isNotEmpty()) {
                Text(
                    text = errores.nombre,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            // Correo electrónico
            Text("Correo electrónico", fontSize = 12.sp, color = verde)
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = correo,
                onValueChange = {
                    correo = it
                    actualizarErrores()
                },
                isError = intentoRegistro && errores.correo.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            if (intentoRegistro && errores.correo.isNotEmpty()) {
                Text(
                    text = errores.correo,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Contraseña
            Text("Contraseña", fontSize = 12.sp, color = verde)
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = contrasena,
                onValueChange = {
                    contrasena = it
                    actualizarErrores()
                },
                isError = intentoRegistro && errores.contrasena.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            if (intentoRegistro && errores.contrasena.isNotEmpty()) {
                Text(
                    text = errores.contrasena,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Confirmar contraseña
            Text("Confirme su contraseña", fontSize = 12.sp, color = verde)
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = confirmarContrasena,
                onValueChange = {
                    confirmarContrasena = it
                    actualizarErrores()
                },
                isError = intentoRegistro && errores.confirmarContrasena.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            if (intentoRegistro && errores.confirmarContrasena.isNotEmpty()) {
                Text(
                    text = errores.confirmarContrasena,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    intentoRegistro = true
                    mensajeExito = ""
                    errores = validarRegistro(
                        nombre = nombre,
                        correo = correo,
                        contrasena = contrasena,
                        confirmarContrasena = confirmarContrasena
                    )
                    if (errores.esValido() && !guardando) {
                        guardando = true

                        scope.launch {
                            try {
                                val registrado = withContext(Dispatchers.IO) {
                                    databaseHelper.registrarUsuario(
                                        nombre = nombre,
                                        correo = correo,
                                        contrasena = contrasena,
                                        perfil = tipoUsuario
                                    )
                                }
                                if (registrado) {
                                    mensajeExito = "Cuenta creada correctamente"
                                    navController?.navigate("codigo_verificacion")
                                } else {
                                    mensajeExito = ""
                                    errores = errores.copy(
                                        correo = "No se pudo registrar. Comprueba si el correo ya existe"
                                    )
                                }
                            } catch (e: Exception) {
                                android.util.Log.e("ESUCAR_REGISTRO", "Error al registrar usuario", e)
                                mensajeExito = "No se pudo completar el registro"
                            } finally {
                                guardando = false
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                enabled = !guardando,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = verde,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (guardando) "Creando..." else "Crear cuenta",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (mensajeExito.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = mensajeExito,
                    color = verde,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Pie institucional
            PieInstitucional()
        }
    }
}
