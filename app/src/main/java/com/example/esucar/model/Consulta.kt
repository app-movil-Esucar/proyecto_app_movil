package com.example.esucar.model

data class Consulta(
    val id: Int = 0,
    val estudianteNombre: String,
    val codigoAsignatura: String,
    val pregunta: String,
    val respuesta: String? = null,
    val fecha: String,
    val estado: String = "Pendiente"
)
