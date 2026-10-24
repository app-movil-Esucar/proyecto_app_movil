package com.example.esucar.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import android.util.Base64
import android.content.ContentValues
import java.security.MessageDigest
import com.example.esucar.model.Usuario

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "esucar.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val crearTablaUsuarios = """
        CREATE TABLE usuarios (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT NOT NULL,
            correo TEXT NOT NULL UNIQUE,
            password_hash TEXT NOT NULL,
            perfil TEXT NOT NULL
        )
    """.trimIndent()
        db.execSQL(crearTablaUsuarios)
    }
    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        // Aquí gestionaremos futuras versiones
        // de la base de datos.
    }
    //funcion protegerContrasena
    private fun protegerContrasena(contrasena: String): String {
        val salt = ByteArray(16)
        SecureRandom().nextBytes(salt)

        val spec = PBEKeySpec(
            contrasena.toCharArray(),
            salt,
            120000,
            256
        )
        val hash = try {
            SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                .generateSecret(spec)
                .encoded
        } finally {
            spec.clearPassword()
        }

        val saltTexto = Base64.encodeToString(salt, Base64.NO_WRAP)
        val hashTexto = Base64.encodeToString(hash, Base64.NO_WRAP)

        return "$saltTexto:$hashTexto"
    }
    //Funcion registarUsuario

    fun registrarUsuario(
        nombre: String,
        correo: String,
        contrasena: String,
        perfil: String
    ): Boolean {

        val db = writableDatabase
        val datos = ContentValues().apply {
            put("nombre", nombre.trim())
            put("correo", correo.trim().lowercase())
            put("password_hash", protegerContrasena(contrasena))
            put("perfil", perfil)
        }
        val resultado = db.insert("usuarios", null, datos)
        db.close()
        return resultado != -1L
    }
    //Funcion verificarContrasena
    private fun verificarContrasena(
        contrasena: String,
        hashGuardado: String
    ): Boolean {

        val partes = hashGuardado.split(":")
        if (partes.size != 2) {
            return false
        }
        return try {
            val salt = Base64.decode(partes[0], Base64.NO_WRAP)
            val hashOriginal = Base64.decode(partes[1], Base64.NO_WRAP)

            val spec = PBEKeySpec(contrasena.toCharArray(), salt, 120000, 256)
            val hashIngresado = try {
                SecretKeyFactory
                    .getInstance("PBKDF2WithHmacSHA1")
                    .generateSecret(spec)
                    .encoded
            } finally {
                spec.clearPassword()
            }
            MessageDigest.isEqual(hashOriginal, hashIngresado)
        } catch (e: Exception) {
            false
        }
    }
    //funcion iniciarSesion

    fun iniciarSesion(
        correo: String,
        contrasena: String
    ): Usuario? {
        val db = readableDatabase
        val cursor = db.query(
            "usuarios",
            arrayOf("id", "nombre", "correo", "password_hash", "perfil"),
            "correo = ?",
            arrayOf(correo.trim().lowercase()),
            null,
            null,
            null
        )
        return cursor.use {
            if (!it.moveToFirst()) {
                return@use null
            }
            val hashGuardado = it.getString(it.getColumnIndexOrThrow("password_hash"))
            if (!verificarContrasena(contrasena, hashGuardado)) {
                return@use null
            }
            Usuario(
                id = it.getInt(it.getColumnIndexOrThrow("id")),
                nombre = it.getString(it.getColumnIndexOrThrow("nombre")),
                correo = it.getString(it.getColumnIndexOrThrow("correo")),
                perfil = it.getString(it.getColumnIndexOrThrow("perfil"))
            )
        }
    }
}
