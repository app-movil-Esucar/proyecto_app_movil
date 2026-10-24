package com.example.esucar.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esucar.R
import com.example.esucar.ui.components.EncabezadoEsucar
import com.example.esucar.ui.components.PieInstitucional
@Composable
fun PantallaAcceso(
    onIniciarSesion: () -> Unit,
    onCrearCuenta: () -> Unit
) {
    val verde = Color(0xFF104D30)
    val fondo = Color(0xFFF1F3F0)
    Column(
        modifier = Modifier.fillMaxSize()
            .background(fondo)
    ) {
        //Encabezado
        EncabezadoEsucar()

        //Contenido de bienvenida
        Column(
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¡Bienvenido!",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF172B20)
            )
            Spacer(modifier = Modifier.height(35.dp))
            Button(
                onClick = onIniciarSesion,
                modifier = Modifier.fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = verde,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Iniciar sesión",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Button(
                onClick = onCrearCuenta,
                modifier = Modifier.fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = verde,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Crear una cuenta",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        // Pie de página
        PieInstitucional()
    }
}