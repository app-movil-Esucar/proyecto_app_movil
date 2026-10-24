package com.example.esucar.validation

import android.util.Patterns

data class ErroresRegistro(
    val nombre: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val confirmarContrasena: String = ""
) {
    fun esValido(): Boolean {
        return nombre.isEmpty() &&
                correo.isEmpty() &&
                contrasena.isEmpty() &&
                confirmarContrasena.isEmpty()
    }
}

fun validarRegistro(
    nombre: String,
    correo: String,
    contrasena: String,
    confirmarContrasena: String
): ErroresRegistro {

    val errorNombre = when {
        nombre.isBlank() -> "Ingresa tu nombre"
        else -> ""
    }

    val errorCorreo = when {
        correo.isBlank() -> "Ingresa tu correo electrónico"
        !correo.trim().endsWith("@carabineros.cl", ignoreCase = true) ->
            "Debes utilizar un correo @carabineros.cl"
        !Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches() ->
            "Ingresa un correo electrónico válido"
        else -> ""
    }

    val errorContrasena = when {
        contrasena.isBlank() -> "Ingresa una contraseña"
        contrasena.length < 8 ->
            "La contraseña debe tener al menos 8 caracteres"
        else -> ""
    }

    val errorConfirmacion = when {
        confirmarContrasena.isBlank() -> "Confirma tu contraseña"
        contrasena != confirmarContrasena ->
            "Las contraseñas no coinciden"
        else -> ""
    }

    return ErroresRegistro(
        nombre = errorNombre,
        correo = errorCorreo,
        contrasena = errorContrasena,
        confirmarContrasena = errorConfirmacion
    )
}


