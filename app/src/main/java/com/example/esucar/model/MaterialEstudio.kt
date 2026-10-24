package com.example.esucar.model

data class MaterialEstudio(
    val nombre: String,
    val estado: String = "Pendiente",
    val id: Int = 0,
    val codigoAsignatura: String = "",
    val unidad: String = "Unidad 1",
    val tipo: String = "PDF",
    val fecha: String = "10 Oct 2024"
)
