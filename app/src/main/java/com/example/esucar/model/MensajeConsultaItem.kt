package com.example.esucar.model

data class MensajeConsultaItem(
    val id: Int = 0,
    val consultaId: Int,
    val remitenteNombre: String,
    val perfilRemitente: String, // "Estudiante" o "Docente"
    val mensaje: String,
    val fecha: String
)
