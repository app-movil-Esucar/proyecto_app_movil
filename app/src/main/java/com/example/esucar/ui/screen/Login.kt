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

@Composable
fun PantallaLogin() {

    var tipoUsuario by remember{
        mutableStateOf("Estudiante")
    }
    var usuarioInstitucional by remember{
        mutableStateOf("")
    }
    var contrasena by remember {
        mutableStateOf("")
    }

    val verde= Color(0Xff104d30)
    val fondo= Color(0xFFF1F3F0)

    Column(
        modifier = Modifier.fillMaxSize()
            .background(fondo)
    )   {
        //seccion superior
        Column(
            modifier = Modifier.fillMaxWidth()
                .weight(0.40f)
                .background(verde),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter=painterResource(R.drawable.logo2),
                contentDescription = "Escudo ESUCAR",
                modifier = Modifier.size(105.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Escuela de Suboficiales",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Repositorio de Estudio • ESUCAR",
                color = Color(0xFFE5B000),
                fontSize = 10.sp
            )
        }
        //para linea amarilla y naranja
        Row(
            modifier = Modifier.fillMaxWidth()
                .height(6.dp)
        ) {//mitad amarilla
            Box(
                modifier = Modifier.weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFFE5B000))
            )//mitad naranja
            Box(
                modifier = Modifier.weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFFE85D18))
            )

        }
        //seccion inferior
        Column(
            modifier = Modifier.fillMaxWidth()
                .weight(0.6f)
                .padding(16.dp)
        ){
            Text(
                text = "Iniciar sesión",
                color = Color(0xFF172B20),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .background(
                        Color(0xFFE4EAE4),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                listOf("Estudiante", "Docente").forEach { usuario ->
                    Button(
                        onClick = {
                            tipoUsuario = usuario
                        },
                        modifier = Modifier.weight(1f)
                            .height(40.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                                if (tipoUsuario == usuario)
                                    Color.White
                                else
                                    Color(0xFFE4EAE4),
                            contentColor =
                                if(tipoUsuario == usuario)
                                    verde
                                else
                                    Color.DarkGray
                        ),
                        contentPadding = PaddingValues(0.dp),
                        elevation = null
                    ){
                        Text(
                            text = usuario,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
            //para usuario institucional
            Text(
                text = "Usuario institucional",
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
        }
    }
}