package com.example.esucar.model

data class Asignatura(
    val codigo: String,
    val nombre: String,
    val area: String,
    val docente: String,
    val semestre: Int,
    val totalUnidades: Int,
    val unidadesCompletadas: Int,
    val progreso: Int
)
