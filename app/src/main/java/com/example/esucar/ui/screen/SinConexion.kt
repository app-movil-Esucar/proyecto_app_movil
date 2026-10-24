package com.example.esucar.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.FileDownloadDone
import androidx.compose.material.icons.outlined.SignalWifiOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.esucar.ui.components.PieInstitucional

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaSinConexion(navController: NavController) {
    val context = LocalContext.current
    val verde = Color(0xFF104D30)

    val materialesDescargados = listOf(
        "Código Penal - Compendio 2024 (PDF)",
        "Guía de Procedimientos en Terreno (PDF)",
        "Resumen Derechos Humanos en la Función Policial (PDF)"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Modo Sin Conexión", color = Color.White) },
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
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Icon(
                imageVector = Icons.Outlined.SignalWifiOff,
                contentDescription = "Sin Conexión",
                tint = Color(0xFFE85D18),
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Sin Conexión a Internet",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = verde
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Estás trabajando en modo fuera de línea. Puedes seguir consultando tus documentos guardados localmente.",
                fontSize = 13.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    Toast.makeText(context, "Verificando estado de red...", Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = verde)
            ) {
                Text("Reintentar Conexión", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Materiales Disponibles Fuera de Línea",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = verde,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(materialesDescargados) { material ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.FileDownloadDone,
                                contentDescription = null,
                                tint = verde,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = material,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }
    }
}
