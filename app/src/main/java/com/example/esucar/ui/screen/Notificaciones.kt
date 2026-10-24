package com.example.esucar.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
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
import com.example.esucar.model.Notificacion
import com.example.esucar.ui.components.PieInstitucional

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaNotificaciones(navController: NavController) {
    val context = LocalContext.current
    val databaseHelper = remember(context) { DatabaseHelper(context.applicationContext) }

    val verde = Color(0xFF104D30)

    var listaNotificaciones by remember {
        mutableStateOf(databaseHelper.obtenerNotificaciones())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notificaciones", color = Color.White) },
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
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Avisos Recientes",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = verde
                )
                TextButton(
                    onClick = {
                        databaseHelper.marcarNotificacionesComoLeidas()
                        listaNotificaciones = databaseHelper.obtenerNotificaciones()
                    }
                ) {
                    Text("Marcar todo como leído", color = verde, fontSize = 12.sp)
                }
            }

            if (listaNotificaciones.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No tienes notificaciones por el momento", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(listaNotificaciones) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (item.leida) Color.White else Color(0xFFEAF2EE)
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = verde.copy(alpha = 0.1f),
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Outlined.Notifications,
                                            contentDescription = null,
                                            tint = verde,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.titulo,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = verde
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = item.detalle,
                                        fontSize = 13.sp,
                                        color = Color.DarkGray
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = item.fecha,
                                        fontSize = 11.sp,
                                        color = Color.Gray
                                    )
                                }

                                if (!item.leida) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .background(verde, CircleShape)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
