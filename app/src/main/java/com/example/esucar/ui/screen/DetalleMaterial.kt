package com.example.esucar.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun PantallaDetalleMaterial(
    navController: NavController,
    nombreMaterial: String,
    codigo: String,
    unidad: String
) {
    val context = LocalContext.current
    val verde = Color(0xFF104D30)
    val fondo = Color(0xFFF1F3F0)
    val nombreAsignatura = when (codigo) {
        "DPE201" -> "Derecho Penal I"
        "GAP202" -> "Gestión Administrativa"
        "DOI203" -> "Doctrina Institucional"
        else -> "Asignatura no encontrada"
    }

    Scaffold(
        bottomBar = {
            Button(
                onClick = {
                    navController.navigate(
                        "nueva_consulta?codigo=$codigo&unidad=$unidad&material=${android.net.Uri.encode(nombreMaterial)}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE5B000),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Consultar al docente sobre este material")
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(fondo)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            // Volver a la asignatura
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        navController.navigate("mis_asignaturas")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Volver"
                    )
                }

                Text(
                    text = "$nombreAsignatura · Unidad $unidad",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Vista de ejemplo del documento
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE0EEE5)
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Description,
                        contentDescription = "Documento PDF",
                        tint = verde,
                        modifier = Modifier.size(75.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = nombreMaterial,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF172B20)
            )

            Text(
                text = "Material de ejemplo · PDF · 8 páginas · 1,2 MB",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = {
                        Toast.makeText(
                            context,
                            "Para una futura actualización",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = verde
                    )
                ) {
                    Text("Abrir")
                }

                OutlinedButton(
                    onClick = {
                        Toast.makeText(
                            context,
                            "Para una futura actualización",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Descargar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(14.dp)
                ) {
                    Text(
                        text = "Marcar como estudiado",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "Suma a tu avance de la Unidad 3",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
