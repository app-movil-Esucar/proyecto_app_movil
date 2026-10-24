package com.example.esucar.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Base64
import com.example.esucar.model.Asignatura
import com.example.esucar.model.Consulta
import com.example.esucar.model.MaterialEstudio
import com.example.esucar.model.MensajeConsultaItem
import com.example.esucar.model.Notificacion
import com.example.esucar.model.Usuario
import java.security.MessageDigest
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "esucar.db", null, 3) {

    override fun onCreate(db: SQLiteDatabase) {
        // 1. Tabla Usuarios
        db.execSQL("""
            CREATE TABLE usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                correo TEXT NOT NULL UNIQUE,
                password_hash TEXT NOT NULL,
                perfil TEXT NOT NULL
            )
        """.trimIndent())

        // 2. Tabla Asignaturas
        db.execSQL("""
            CREATE TABLE asignaturas (
                codigo TEXT PRIMARY KEY,
                nombre TEXT NOT NULL,
                area TEXT NOT NULL,
                docente TEXT NOT NULL,
                semestre INTEGER NOT NULL,
                total_unidades INTEGER NOT NULL,
                unidades_completadas INTEGER NOT NULL,
                progreso INTEGER NOT NULL
            )
        """.trimIndent())

        // 3. Tabla Materiales
        db.execSQL("""
            CREATE TABLE materiales (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                codigo_asignatura TEXT NOT NULL,
                unidad TEXT NOT NULL,
                nombre TEXT NOT NULL,
                tipo TEXT NOT NULL,
                estado TEXT NOT NULL,
                fecha TEXT NOT NULL
            )
        """.trimIndent())

        // 4. Tabla Consultas
        db.execSQL("""
            CREATE TABLE consultas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                estudiante_nombre TEXT NOT NULL,
                codigo_asignatura TEXT NOT NULL,
                pregunta TEXT NOT NULL,
                respuesta TEXT,
                fecha TEXT NOT NULL,
                estado TEXT NOT NULL
            )
        """.trimIndent())

        // 5. Tabla Mensajes Consulta (Hilo de Chat)
        db.execSQL("""
            CREATE TABLE mensajes_consulta (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                consulta_id INTEGER NOT NULL,
                remitente_nombre TEXT NOT NULL,
                perfil_remitente TEXT NOT NULL,
                mensaje TEXT NOT NULL,
                fecha TEXT NOT NULL
            )
        """.trimIndent())

        // 6. Tabla Notificaciones
        db.execSQL("""
            CREATE TABLE notificaciones (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                detalle TEXT NOT NULL,
                fecha TEXT NOT NULL,
                leida INTEGER NOT NULL DEFAULT 0
            )
        """.trimIndent())

        // ================= PRE-CARGA DE DATOS INICIALES =================

        // Usuarios iniciales
        db.insert("usuarios", null, ContentValues().apply {
            put("nombre", "Carabinero Alumno")
            put("correo", "estudiante@esucar.cl")
            put("password_hash", protegerContrasena("123456"))
            put("perfil", "Estudiante")
        })

        db.insert("usuarios", null, ContentValues().apply {
            put("nombre", "Profesor Juan Ramírez")
            put("correo", "docente@esucar.cl")
            put("password_hash", protegerContrasena("123456"))
            put("perfil", "Docente")
        })

        // Asignaturas iniciales
        val listaAsignaturas = listOf(
            Asignatura("DER-101", "Derecho Penal I", "Jurídica", "Prof. Juan Ramírez", 1, 4, 3, 75),
            Asignatura("PRO-201", "Procedimientos Policiales", "Operativa", "Prof. Carlos Mendoza", 2, 4, 2, 50),
            Asignatura("DDHH-102", "Derechos Humanos", "Jurídica", "Prof. Ana María Silva", 1, 3, 3, 100),
            Asignatura("TAC-301", "Tácticas Policiales", "Operativa", "Prof. Roberto Gómez", 3, 3, 1, 33)
        )

        for (asig in listaAsignaturas) {
            db.insert("asignaturas", null, ContentValues().apply {
                put("codigo", asig.codigo)
                put("nombre", asig.nombre)
                put("area", asig.area)
                put("docente", asig.docente)
                put("semestre", asig.semestre)
                put("total_unidades", asig.totalUnidades)
                put("unidades_completadas", asig.unidadesCompletadas)
                put("progreso", asig.progreso)
            })
        }

        // Materiales iniciales
        val listaMateriales = listOf(
            MaterialEstudio(id = 1, codigoAsignatura = "DER-101", unidad = "Unidad 1", nombre = "Código Penal - Compendio 2024", tipo = "PDF", estado = "Leído", fecha = "10 Sep 2024"),
            MaterialEstudio(id = 2, codigoAsignatura = "DER-101", unidad = "Unidad 1", nombre = "Guía de Ejercicios Prácticos", tipo = "PDF", estado = "Leído", fecha = "15 Sep 2024"),
            MaterialEstudio(id = 3, codigoAsignatura = "DER-101", unidad = "Unidad 2", nombre = "Jurisprudencia Reincidencia Faltas", tipo = "PDF", estado = "Pendiente", fecha = "01 Oct 2024"),
            MaterialEstudio(id = 4, codigoAsignatura = "DER-101", unidad = "Unidad 2", nombre = "Video Explicativo Legítima Defensa", tipo = "MP4", estado = "Pendiente", fecha = "05 Oct 2024"),
            MaterialEstudio(id = 5, codigoAsignatura = "PRO-201", unidad = "Unidad 1", nombre = "Manual de Procedimientos en Terreno", tipo = "PDF", estado = "Leído", fecha = "20 Sep 2024"),
            MaterialEstudio(id = 6, codigoAsignatura = "PRO-201", unidad = "Unidad 2", nombre = "Modelo de Parte Policial Tránsito", tipo = "DOCX", estado = "Pendiente", fecha = "08 Oct 2024"),
            MaterialEstudio(id = 7, codigoAsignatura = "DDHH-102", unidad = "Unidad 1", nombre = "Derechos Humanos en Función Policial", tipo = "PDF", estado = "Leído", fecha = "05 Sep 2024")
        )

        for (mat in listaMateriales) {
            db.insert("materiales", null, ContentValues().apply {
                put("codigo_asignatura", mat.codigoAsignatura)
                put("unidad", mat.unidad)
                put("nombre", mat.nombre)
                put("tipo", mat.tipo)
                put("estado", mat.estado)
                put("fecha", mat.fecha)
            })
        }

        // Consultas iniciales
        val listaConsultas = listOf(
            Consulta(
                id = 1,
                estudianteNombre = "Carabinero A. González",
                codigoAsignatura = "DER-101",
                pregunta = "Profesor, en el artículo 15 del Código Penal, ¿cómo se aplica la agravante de reincidencia en faltas menores?",
                respuesta = "Estimado, en faltas menores la reincidencia opera según el articulado 16, siempre que no hayan transcurrido más de 6 meses.",
                fecha = "14 Oct, 10:15 AM",
                estado = "Respondida"
            ),
            Consulta(
                id = 2,
                estudianteNombre = "Carabinero M. Silva",
                codigoAsignatura = "DER-101",
                pregunta = "¿Cuáles son los requisitos legales inmediatos para la detención en flagrancia?",
                respuesta = null,
                fecha = "Ayer, 18:30 PM",
                estado = "Pendiente"
            )
        )

        for (cons in listaConsultas) {
            db.insert("consultas", null, ContentValues().apply {
                put("estudiante_nombre", cons.estudianteNombre)
                put("codigo_asignatura", cons.codigoAsignatura)
                put("pregunta", cons.pregunta)
                put("respuesta", cons.respuesta)
                put("fecha", cons.fecha)
                put("estado", cons.estado)
            })
        }

        // Pre-carga del Hilo de Chat inicial para la consulta #1
        db.insert("mensajes_consulta", null, ContentValues().apply {
            put("consulta_id", 1)
            put("remitente_nombre", "Carabinero A. González")
            put("perfil_remitente", "Estudiante")
            put("mensaje", "Profesor, en el artículo 15 del Código Penal, ¿cómo se aplica la agravante de reincidencia en faltas menores?")
            put("fecha", "14 Oct, 10:15 AM")
        })

        db.insert("mensajes_consulta", null, ContentValues().apply {
            put("consulta_id", 1)
            put("remitente_nombre", "Prof. Juan Ramírez")
            put("perfil_remitente", "Docente")
            put("mensaje", "Estimado, en faltas menores la reincidencia opera según el articulado 16, siempre que no hayan transcurrido más de 6 meses desde el fallo anterior.")
            put("fecha", "14 Oct, 11:30 AM")
        })

        // Pre-carga del Hilo de Chat para la consulta #2
        db.insert("mensajes_consulta", null, ContentValues().apply {
            put("consulta_id", 2)
            put("remitente_nombre", "Carabinero M. Silva")
            put("perfil_remitente", "Estudiante")
            put("mensaje", "¿Cuáles son los requisitos legales inmediatos para la detención en flagrancia?")
            put("fecha", "Ayer, 18:30 PM")
        })

        // Notificaciones iniciales
        val listaNotificaciones = listOf(
            Notificacion(1, "Respuesta a tu consulta", "El Profesor Ramírez ha respondido tu duda sobre el Código Penal.", "Hace 10 min", false),
            Notificacion(2, "Nuevo Material Disponible", "Se ha publicado la Guía de Estudio de la Unidad 2 en Procedimientos Policiales.", "Hoy, 09:30 AM", false),
            Notificacion(3, "Recordatorio de Evaluación", "Tu examen de Derechos Humanos está programado para este viernes.", "Ayer, 16:45 PM", true)
        )

        for (notif in listaNotificaciones) {
            db.insert("notificaciones", null, ContentValues().apply {
                put("titulo", notif.titulo)
                put("detalle", notif.detalle)
                put("fecha", notif.fecha)
                put("leida", if (notif.leida) 1 else 0)
            })
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        db.execSQL("DROP TABLE IF EXISTS asignaturas")
        db.execSQL("DROP TABLE IF EXISTS materiales")
        db.execSQL("DROP TABLE IF EXISTS consultas")
        db.execSQL("DROP TABLE IF EXISTS mensajes_consulta")
        db.execSQL("DROP TABLE IF EXISTS notificaciones")
        onCreate(db)
    }

    // Hash de contraseña
    private fun protegerContrasena(contrasena: String): String {
        val salt = ByteArray(16)
        SecureRandom().nextBytes(salt)
        val spec = PBEKeySpec(contrasena.toCharArray(), salt, 120000, 256)
        val hash = try {
            SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(spec).encoded
        } finally {
            spec.clearPassword()
        }
        return "${Base64.encodeToString(salt, Base64.NO_WRAP)}:${Base64.encodeToString(hash, Base64.NO_WRAP)}"
    }

    private fun verificarContrasena(contrasena: String, hashGuardado: String): Boolean {
        val partes = hashGuardado.split(":")
        if (partes.size != 2) return false
        return try {
            val salt = Base64.decode(partes[0], Base64.NO_WRAP)
            val hashOriginal = Base64.decode(partes[1], Base64.NO_WRAP)
            val spec = PBEKeySpec(contrasena.toCharArray(), salt, 120000, 256)
            val hashIngresado = try {
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(spec).encoded
            } finally {
                spec.clearPassword()
            }
            MessageDigest.isEqual(hashOriginal, hashIngresado)
        } catch (e: Exception) {
            false
        }
    }

    // Métodos Usuarios
    fun registrarUsuario(nombre: String, correo: String, contrasena: String, perfil: String): Boolean {
        val db = writableDatabase
        val correoLimpio = correo.trim().lowercase()
        val datos = ContentValues().apply {
            put("nombre", nombre.trim())
            put("correo", correoLimpio)
            put("password_hash", protegerContrasena(contrasena))
            put("perfil", perfil)
        }
        val resultado = db.insert("usuarios", null, datos)
        db.close()
        return resultado != -1L
    }

    fun iniciarSesion(correo: String, contrasena: String): Usuario? {
        val db = readableDatabase
        val correoLimpio = correo.trim().lowercase()
        val cursor = db.query(
            "usuarios",
            arrayOf("id", "nombre", "correo", "password_hash", "perfil"),
            "correo = ?",
            arrayOf(correoLimpio),
            null, null, null
        )
        return cursor.use {
            if (!it.moveToFirst()) return@use null
            val hashGuardado = it.getString(it.getColumnIndexOrThrow("password_hash"))
            if (!verificarContrasena(contrasena, hashGuardado)) return@use null
            Usuario(
                id = it.getInt(it.getColumnIndexOrThrow("id")),
                nombre = it.getString(it.getColumnIndexOrThrow("nombre")),
                correo = it.getString(it.getColumnIndexOrThrow("correo")),
                perfil = it.getString(it.getColumnIndexOrThrow("perfil"))
            )
        }
    }

    fun obtenerTodosLosUsuarios(): List<Usuario> {
        val lista = mutableListOf<Usuario>()
        val db = readableDatabase
        val cursor = db.query("usuarios", arrayOf("id", "nombre", "correo", "perfil"), null, null, null, null, "id ASC")
        cursor.use {
            while (it.moveToNext()) {
                lista.add(
                    Usuario(
                        id = it.getInt(it.getColumnIndexOrThrow("id")),
                        nombre = it.getString(it.getColumnIndexOrThrow("nombre")),
                        correo = it.getString(it.getColumnIndexOrThrow("correo")),
                        perfil = it.getString(it.getColumnIndexOrThrow("perfil"))
                    )
                )
            }
        }
        return lista
    }

    // Métodos Asignaturas
    fun obtenerAsignaturas(): List<Asignatura> {
        val lista = mutableListOf<Asignatura>()
        val db = readableDatabase
        val cursor = db.query("asignaturas", null, null, null, null, null, "nombre ASC")
        cursor.use {
            while (it.moveToNext()) {
                lista.add(
                    Asignatura(
                        codigo = it.getString(it.getColumnIndexOrThrow("codigo")),
                        nombre = it.getString(it.getColumnIndexOrThrow("nombre")),
                        area = it.getString(it.getColumnIndexOrThrow("area")),
                        docente = it.getString(it.getColumnIndexOrThrow("docente")),
                        semestre = it.getInt(it.getColumnIndexOrThrow("semestre")),
                        totalUnidades = it.getInt(it.getColumnIndexOrThrow("total_unidades")),
                        unidadesCompletadas = it.getInt(it.getColumnIndexOrThrow("unidades_completadas")),
                        progreso = it.getInt(it.getColumnIndexOrThrow("progreso"))
                    )
                )
            }
        }
        return lista
    }

    fun obtenerAsignaturaPorCodigo(codigo: String): Asignatura? {
        val db = readableDatabase
        val cursor = db.query("asignaturas", null, "codigo = ?", arrayOf(codigo), null, null, null)
        return cursor.use {
            if (!it.moveToFirst()) return@use null
            Asignatura(
                codigo = it.getString(it.getColumnIndexOrThrow("codigo")),
                nombre = it.getString(it.getColumnIndexOrThrow("nombre")),
                area = it.getString(it.getColumnIndexOrThrow("area")),
                docente = it.getString(it.getColumnIndexOrThrow("docente")),
                semestre = it.getInt(it.getColumnIndexOrThrow("semestre")),
                totalUnidades = it.getInt(it.getColumnIndexOrThrow("total_unidades")),
                unidadesCompletadas = it.getInt(it.getColumnIndexOrThrow("unidades_completadas")),
                progreso = it.getInt(it.getColumnIndexOrThrow("progreso"))
            )
        }
    }

    // Métodos Materiales
    fun obtenerMaterialesPorAsignatura(codigoAsignatura: String): List<MaterialEstudio> {
        val lista = mutableListOf<MaterialEstudio>()
        val db = readableDatabase
        val cursor = db.query("materiales", null, "codigo_asignatura = ?", arrayOf(codigoAsignatura), null, null, "id DESC")
        cursor.use {
            while (it.moveToNext()) {
                lista.add(
                    MaterialEstudio(
                        id = it.getInt(it.getColumnIndexOrThrow("id")),
                        codigoAsignatura = it.getString(it.getColumnIndexOrThrow("codigo_asignatura")),
                        unidad = it.getString(it.getColumnIndexOrThrow("unidad")),
                        nombre = it.getString(it.getColumnIndexOrThrow("nombre")),
                        tipo = it.getString(it.getColumnIndexOrThrow("tipo")),
                        estado = it.getString(it.getColumnIndexOrThrow("estado")),
                        fecha = it.getString(it.getColumnIndexOrThrow("fecha"))
                    )
                )
            }
        }
        return lista
    }

    fun insertarMaterial(codigoAsignatura: String, unidad: String, nombre: String, tipo: String): Boolean {
        val db = writableDatabase
        val datos = ContentValues().apply {
            put("codigo_asignatura", codigoAsignatura)
            put("unidad", unidad)
            put("nombre", nombre)
            put("tipo", tipo)
            put("estado", "Pendiente")
            put("fecha", "Hoy")
        }
        val resultado = db.insert("materiales", null, datos)
        db.close()
        return resultado != -1L
    }

    fun eliminarMaterial(id: Int): Boolean {
        val db = writableDatabase
        val filas = db.delete("materiales", "id = ?", arrayOf(id.toString()))
        db.close()
        return filas > 0
    }

    // Métodos Consultas
    fun obtenerConsultas(): List<Consulta> {
        val lista = mutableListOf<Consulta>()
        val db = readableDatabase
        val cursor = db.query("consultas", null, null, null, null, null, "id DESC")
        cursor.use {
            while (it.moveToNext()) {
                val resp = it.getString(it.getColumnIndexOrThrow("respuesta"))
                lista.add(
                    Consulta(
                        id = it.getInt(it.getColumnIndexOrThrow("id")),
                        estudianteNombre = it.getString(it.getColumnIndexOrThrow("estudiante_nombre")),
                        codigoAsignatura = it.getString(it.getColumnIndexOrThrow("codigo_asignatura")),
                        pregunta = it.getString(it.getColumnIndexOrThrow("pregunta")),
                        respuesta = if (resp.isNullOrEmpty()) null else resp,
                        fecha = it.getString(it.getColumnIndexOrThrow("fecha")),
                        estado = it.getString(it.getColumnIndexOrThrow("estado"))
                    )
                )
            }
        }
        return lista
    }

    fun obtenerConsultaPorId(id: Int): Consulta? {
        val db = readableDatabase
        val cursor = db.query("consultas", null, "id = ?", arrayOf(id.toString()), null, null, null)
        return cursor.use {
            if (!it.moveToFirst()) return@use null
            val resp = it.getString(it.getColumnIndexOrThrow("respuesta"))
            Consulta(
                id = it.getInt(it.getColumnIndexOrThrow("id")),
                estudianteNombre = it.getString(it.getColumnIndexOrThrow("estudiante_nombre")),
                codigoAsignatura = it.getString(it.getColumnIndexOrThrow("codigo_asignatura")),
                pregunta = it.getString(it.getColumnIndexOrThrow("pregunta")),
                respuesta = if (resp.isNullOrEmpty()) null else resp,
                fecha = it.getString(it.getColumnIndexOrThrow("fecha")),
                estado = it.getString(it.getColumnIndexOrThrow("estado"))
            )
        }
    }

    fun insertarConsulta(estudianteNombre: String, codigoAsignatura: String, pregunta: String): Boolean {
        val db = writableDatabase
        val horaActual = SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault()).format(Date())

        val datos = ContentValues().apply {
            put("estudiante_nombre", estudianteNombre)
            put("codigo_asignatura", codigoAsignatura)
            put("pregunta", pregunta)
            put("fecha", horaActual)
            put("estado", "Pendiente")
        }
        val consultaId = db.insert("consultas", null, datos)

        if (consultaId != -1L) {
            // Guardar primer mensaje en la tabla mensajes_consulta
            val datosMensaje = ContentValues().apply {
                put("consulta_id", consultaId.toInt())
                put("remitente_nombre", estudianteNombre)
                put("perfil_remitente", "Estudiante")
                put("mensaje", pregunta)
                put("fecha", horaActual)
            }
            db.insert("mensajes_consulta", null, datosMensaje)
        }

        db.close()
        return consultaId != -1L
    }

    fun responderConsulta(id: Int, respuesta: String): Boolean {
        val db = writableDatabase
        val datos = ContentValues().apply {
            put("respuesta", respuesta)
            put("estado", "Respondida")
        }
        val filas = db.update("consultas", datos, "id = ?", arrayOf(id.toString()))
        db.close()
        return filas > 0
    }

    fun eliminarConsulta(id: Int): Boolean {
        val db = writableDatabase
        db.delete("mensajes_consulta", "consulta_id = ?", arrayOf(id.toString()))
        val filas = db.delete("consultas", "id = ?", arrayOf(id.toString()))
        db.close()
        return filas > 0
    }

    // Métodos Mensajes Consulta (Hilo de Chat)
    fun obtenerMensajesDeConsulta(consultaId: Int): List<MensajeConsultaItem> {
        val lista = mutableListOf<MensajeConsultaItem>()
        val db = readableDatabase
        val cursor = db.query("mensajes_consulta", null, "consulta_id = ?", arrayOf(consultaId.toString()), null, null, "id ASC")
        cursor.use {
            while (it.moveToNext()) {
                lista.add(
                    MensajeConsultaItem(
                        id = it.getInt(it.getColumnIndexOrThrow("id")),
                        consultaId = it.getInt(it.getColumnIndexOrThrow("consulta_id")),
                        remitenteNombre = it.getString(it.getColumnIndexOrThrow("remitente_nombre")),
                        perfilRemitente = it.getString(it.getColumnIndexOrThrow("perfil_remitente")),
                        mensaje = it.getString(it.getColumnIndexOrThrow("mensaje")),
                        fecha = it.getString(it.getColumnIndexOrThrow("fecha"))
                    )
                )
            }
        }
        return lista
    }

    fun agregarMensajeConsulta(consultaId: Int, remitenteNombre: String, perfilRemitente: String, mensaje: String): Boolean {
        val db = writableDatabase
        val horaActual = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

        val datos = ContentValues().apply {
            put("consulta_id", consultaId)
            put("remitente_nombre", remitenteNombre)
            put("perfil_remitente", perfilRemitente)
            put("mensaje", mensaje)
            put("fecha", horaActual)
        }
        val res = db.insert("mensajes_consulta", null, datos)

        // Si el mensaje lo envía el Docente, actualizar el estado de la consulta a Respondida
        if (perfilRemitente == "Docente") {
            val datosConsulta = ContentValues().apply {
                put("respuesta", mensaje)
                put("estado", "Respondida")
            }
            db.update("consultas", datosConsulta, "id = ?", arrayOf(consultaId.toString()))
        }

        db.close()
        return res != -1L
    }

    // Métodos Notificaciones
    fun obtenerNotificaciones(): List<Notificacion> {
        val lista = mutableListOf<Notificacion>()
        val db = readableDatabase
        val cursor = db.query("notificaciones", null, null, null, null, null, "id DESC")
        cursor.use {
            while (it.moveToNext()) {
                lista.add(
                    Notificacion(
                        id = it.getInt(it.getColumnIndexOrThrow("id")),
                        titulo = it.getString(it.getColumnIndexOrThrow("titulo")),
                        detalle = it.getString(it.getColumnIndexOrThrow("detalle")),
                        fecha = it.getString(it.getColumnIndexOrThrow("fecha")),
                        leida = it.getInt(it.getColumnIndexOrThrow("leida")) == 1
                    )
                )
            }
        }
        return lista
    }

    fun marcarNotificacionesComoLeidas() {
        val db = writableDatabase
        val datos = ContentValues().apply {
            put("leida", 1)
        }
        db.update("notificaciones", datos, null, null)
        db.close()
    }
}
