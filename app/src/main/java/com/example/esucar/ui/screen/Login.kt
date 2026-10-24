package com.example.esucar.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esucar.R
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.esucar.ui.components.EncabezadoEsucar
import com.example.esucar.ui.components.SelectorPerfil
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.MaterialTheme
import com.example.esucar.ui.components.PieInstitucional
import androidx.compose.ui.platform.LocalContext
import com.example.esucar.database.DatabaseHelper
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.navigation.NavController
@Composable
fun PantallaLogin(navController: NavController) {

    var tipoUsuario by remember{ mutableStateOf("Estudiante") }
    var usuarioInstitucional by remember{ mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    val context = LocalContext.current
    val databaseHelper = remember(context) {
        DatabaseHelper(context.applicationContext)
    }
    val scope = rememberCoroutineScope()
    var iniciandoSesion by remember {
        mutableStateOf(false)
    }
    var mensajeError by remember {
        mutableStateOf("")
    }


    val verde= Color(0Xff104d30)
    val fondo= Color(0xFFF1F3F0)

    Column(
        modifier = Modifier.fillMaxSize()
            .background(fondo)
            .safeDrawingPadding()
    ){
        //Encabezado
        EncabezadoEsucar()

        //formulario
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
            Spacer(modifier = Modifier.height(12.dp))

            //selecciona entre estudiante o docente
            SelectorPerfil(
                tipoUsuario = tipoUsuario,
                onSeleccionar = { tipoUsuario = it }
            )
            Spacer(modifier = Modifier.height(18.dp))
            //para usuario institucional
            Text(
                text = "Correo institucional",
                color= Color(0xFF45564B),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = usuarioInstitucional,
                onValueChange = {usuarioInstitucional = it},
                modifier = Modifier.fillMaxWidth(),
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
            //para la contraseña
            Text(
                text="Contraseña",
                color = Color(0xFF45564B),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = contrasena,
                onValueChange = {contrasena = it},
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

            // Recuperar contraseña
            TextButton(
                onClick = {
                    // Pendiente implementar recuperación
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
            Spacer(modifier = Modifier.height(16.dp))


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
                                            navController.navigate("inicio_estudiante")
                                        }
                                        "Docente" -> {
                                            android.widget.Toast.makeText(
                                                context,
                                                "Pantalla docente en preparación",
                                                android.widget.Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            } catch (e: Exception) {
                                android.util.Log.e(
                                    "ESUCAR_LOGIN",
                                    "Error al iniciar sesión",
                                    e
                                )
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
                    text = "Ingresar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            // Pie de la pag
            PieInstitucional()


        }
    }
}