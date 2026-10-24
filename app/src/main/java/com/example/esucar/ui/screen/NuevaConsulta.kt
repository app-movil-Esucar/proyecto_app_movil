package com.example.esucar.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaNuevaConsulta(
    navController: NavController,
    codigo: String,
    unidad: String,
    material: String
) {

    val verde = Color(0xFF104D30)
    val context = LocalContext.current
    val nombreAsignatura = when (codigo) {
        "DPE201" -> "Derecho Penal I"
        "GAP202" -> "Gestión Administrativa"
        "DOI203" -> "Doctrina Institucional"
        else -> ""
    }
    var asignatura by remember(codigo) {
        mutableStateOf(nombreAsignatura)
    }
    var desplegado by remember { mutableStateOf(false) }
    var asunto by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    val asignaturas = listOf(
        "Derecho Penal I",
        "Gestión Administrativa",
        "Doctrina Institucional"
    )

    Scaffold(
        containerColor = Color(0xFFF1F3F0)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        navController.navigate("consultas_estudiante")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Volver"
                    )
                }

                Text(
                    text = "Nueva consulta",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "Envía una pregunta a tu docente",
                fontSize = 13.sp,
                color = Color.Gray
            )

            if (unidad.isNotBlank()) {
                OutlinedTextField(
                    value = "Unidad $unidad",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Unidad seleccionada") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (material.isNotBlank()) {
                OutlinedTextField(
                    value = material,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Material relacionado") },
                    modifier = Modifier.fillMaxWidth()
                )
            }


            // Selección de asignatura
            ExposedDropdownMenuBox(
                expanded = desplegado,
                onExpandedChange = { desplegado = it }
            ) {
                OutlinedTextField(
                    value = asignatura,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Asignatura") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = desplegado
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = desplegado,
                    onDismissRequest = { desplegado = false }
                ) {
                    asignaturas.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                asignatura = opcion
                                desplegado = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = asunto,
                onValueChange = { asunto = it },
                label = { Text("Asunto") },
                placeholder = {
                    Text("Ej.: Duda sobre la guía de ejercicios")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = mensaje,
                onValueChange = { mensaje = it },
                label = { Text("Escribe tu consulta") },
                placeholder = {
                    Text("Describe tu duda para que el docente pueda ayudarte")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                minLines = 5
            )

            Button(
                onClick = {
                    Toast.makeText(
                        context,
                        "Para una futura actualización",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                enabled = asignatura.isNotBlank() &&
                        asunto.isNotBlank() &&
                        mensaje.isNotBlank(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = verde
                )
            ) {
                Text("Enviar consulta")
            }
        }
    }
}
