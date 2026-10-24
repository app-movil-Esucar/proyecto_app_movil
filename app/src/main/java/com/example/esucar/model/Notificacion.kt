package com.example.esucar.model

data class Notificacion(
    val id: Int = 0,
    val titulo: String,
    val detalle: String,
    val fecha: String,
    val leida: Boolean = false
)
