package com.example.esucar.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.School
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
import com.example.esucar.ui.components.BarraNavegacionDocente

data class AsignaturaDocente(
    val codigo: String,
    val nombre: String,
    val estudiantes: Int,
    val materiales: Int,
    val consultasPendientes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicioDocente(navController: NavController) {
    val context = LocalContext.current
    val verde = Color(0xFF104D30)

    val asignaturasDocente = listOf(
        AsignaturaDocente("DER-101", "Derecho Penal I", 45, 12, 3),
        AsignaturaDocente("PRO-201", "Procedimientos Policiales II", 38, 8, 1)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Profesor Juan Ramírez", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text("Módulo Docente • ESUCAR", color = Color(0xFFE5B000), fontSize = 11.sp)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("notificaciones") }) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notificaciones",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        Toast.makeText(context, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show()
                        navController.navigate("acceso") {
                            popUpTo(0) { inclusive = true }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                            contentDescription = "Cerrar Sesión",
                            tint = Color(0xFFFFCDD2)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = verde)
            )
        },
        bottomBar = {
            BarraNavegacionDocente(
                seleccionada = "inicio_docente",
                onSeleccionar = { ruta -> navController.navigate(ruta) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF2F4EF))
                .padding(16.dp)
        ) {
            // Quick Actions Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { navController.navigate("publicar_material") },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = verde)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AddCircle,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Publicar Material", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = { navController.navigate("consultas_docente") },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = verde)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Inbox,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Ver Consultas (4)", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Asignaturas a mi Cargo",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = verde,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(asignaturasDocente) { asig ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Outlined.School,
                                        contentDescription = null,
                                        tint = verde,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = asig.nombre,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = verde
                                        )
                                        Text(
                                            text = "Código: ${asig.codigo} • ${asig.estudiantes} Estudiantes",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                }

                                if (asig.consultasPendientes > 0) {
                                    Surface(
                                        color = Color(0xFFFFEBEE),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text(
                                            text = "${asig.consultasPendientes} pend.",
                                            color = Color(0xFFC62828),
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${asig.materiales} materiales publicados",
                                    fontSize = 12.sp,
                                    color = Color.DarkGray
                                )

                                TextButton(
                                    onClick = { navController.navigate("materiales_docente/${asig.codigo}") }
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Outlined.LibraryBooks,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                        tint = verde
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Gestionar Materiales", color = verde, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
