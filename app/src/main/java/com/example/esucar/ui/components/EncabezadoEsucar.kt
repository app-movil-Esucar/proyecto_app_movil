
package com.example.esucar.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esucar.R

@Composable
fun EncabezadoEsucar() {
    val verde = Color(0xFF104D30)
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        //encabezado institucional, Parte verde con escudo y textos
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(verde),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo2),
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
        //diseño de linea amarilla y naranja del medio
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFFE5B000))
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFFE85D18))
            )
        }
    }
}

