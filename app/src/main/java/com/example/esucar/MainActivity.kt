package com.example.esucar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.esucar.ui.screen.PantallaBienvenida
import com.example.esucar.ui.screen.PantallaAcceso
import com.example.esucar.ui.screen.PantallaLogin
import com.example.esucar.ui.screen.PantallaRegistro
import com.example.esucar.ui.theme.EsucarTheme
import com.example.esucar.ui.screen.PantallaInicioEstudiante
import com.example.esucar.ui.screen.PantallaMisAsignaturas
import com.example.esucar.ui.screen.PantallaDetalleAsignatura
import com.example.esucar.ui.screen.PantallaDetalleMaterial
import com.example.esucar.ui.screen.PantallaMisConsultas
import com.example.esucar.ui.screen.PantallaNuevaConsulta
import com.example.esucar.ui.screen.PantallaCodigoVerificacion
import com.example.esucar.ui.screen.PantallaNotificaciones
import com.example.esucar.ui.screen.PantallaSinConexion
import com.example.esucar.ui.screen.PantallaConversacionConsulta
import com.example.esucar.ui.screen.PantallaMiProgreso
import com.example.esucar.ui.screen.PantallaInicioDocente
import com.example.esucar.ui.screen.PantallaConsultasRecibidas
import com.example.esucar.ui.screen.PantallaResponderConsulta
import com.example.esucar.ui.screen.PantallaMaterialesPorUnidad
import com.example.esucar.ui.screen.PantallaPublicarMaterial

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EsucarTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "portada"
    ) {
        //pantalla: PORTADA
        composable("portada") {
            PantallaBienvenida(
                onIngresar = {
                    navController.navigate("acceso")
                }
            )
        }
        //pantalla: BIENVENIDA / ACCESO
        composable("acceso") {
            PantallaAcceso(
                onIniciarSesion = {
                    navController.navigate("login")
                },
                onCrearCuenta = {
                    navController.navigate("registro")
                }
            )
        }
        //pantalla: LOGIN
        composable("login") {
            PantallaLogin(navController)
        }

        //pantalla: REGISTRO
        composable("registro") {
            PantallaRegistro(navController)
        }

        //pantalla: CODIGO VERIFICACION
        composable("codigo_verificacion") {
            PantallaCodigoVerificacion(navController)
        }

        //pantalla: NOTIFICACIONES
        composable("notificaciones") {
            PantallaNotificaciones(navController)
        }

        //pantalla: SIN CONEXION
        composable("sin_conexion") {
            PantallaSinConexion(navController)
        }

        //pantalla: INICIO ESTUDIANTE
        composable("inicio_estudiante") {
            PantallaInicioEstudiante(navController)
        }

        //pantalla: ASIGNATURAS ESTUDIANTE
        composable("mis_asignaturas") {
            PantallaMisAsignaturas(navController)
        }

        //pantalla: DETALLES ASIGNATURA
        composable("detalle_asignatura/{codigo}") { backStackEntry ->
            val codigo = backStackEntry.arguments?.getString("codigo") ?: ""
            PantallaDetalleAsignatura(
                navController = navController,
                codigo = codigo
            )
        }

        //pantalla: DETALLE MATERIALES
        composable("detalle_material/{nombreMaterial}?codigo={codigo}&unidad={unidad}") { backStackEntry ->
            val nombreMaterial = backStackEntry.arguments?.getString("nombreMaterial") ?: ""
            val codigo = backStackEntry.arguments?.getString("codigo") ?: ""
            val unidad = backStackEntry.arguments?.getString("unidad") ?: ""

            PantallaDetalleMaterial(
                navController = navController,
                nombreMaterial = nombreMaterial,
                codigo = codigo,
                unidad = unidad
            )
        }

        //pantalla: CONSULTAS ESTUDIANTE
        composable("consultas_estudiante") {
            PantallaMisConsultas(navController)
        }

        //pantalla: CONVERSACION CONSULTA
        composable("conversacion/{consultaId}?perfil={perfil}") { backStackEntry ->
            val consultaId = backStackEntry.arguments?.getString("consultaId") ?: "1"
            val perfil = backStackEntry.arguments?.getString("perfil") ?: "Estudiante"
            PantallaConversacionConsulta(
                navController = navController,
                consultaId = consultaId,
                perfilUsuario = perfil
            )
        }
        composable("conversacion/{consultaId}") { backStackEntry ->
            val consultaId = backStackEntry.arguments?.getString("consultaId") ?: "1"
            PantallaConversacionConsulta(
                navController = navController,
                consultaId = consultaId,
                perfilUsuario = "Estudiante"
            )
        }

        //pantalla: PROGRESO ESTUDIANTE
        composable("progreso_estudiante") {
            PantallaMiProgreso(navController)
        }

        //pantalla: NUEVA CONSULTA
        composable("nueva_consulta?codigo={codigo}&unidad={unidad}&material={material}") { backStackEntry ->
            val codigo = backStackEntry.arguments?.getString("codigo") ?: ""
            val unidad = backStackEntry.arguments?.getString("unidad") ?: ""
            val material = backStackEntry.arguments?.getString("material") ?: ""
            PantallaNuevaConsulta(
                navController = navController,
                codigo = codigo,
                unidad = unidad,
                material = material
            )
        }

        // ================= FLUTOS DOCENTE =================
        //pantalla: INICIO DOCENTE
        composable("inicio_docente") {
            PantallaInicioDocente(navController)
        }

        //pantalla: CONSULTAS DOCENTE
        composable("consultas_docente") {
            PantallaConsultasRecibidas(navController)
        }

        //pantalla: RESPONDER CONSULTA
        composable("responder_consulta/{consultaId}") { backStackEntry ->
            val consultaId = backStackEntry.arguments?.getString("consultaId") ?: "1"
            PantallaResponderConsulta(
                navController = navController,
                consultaId = consultaId
            )
        }

        //pantalla: MATERIALES DOCENTE
        composable("materiales_docente") {
            PantallaMaterialesPorUnidad(navController = navController, codigo = "DER-101")
        }

        composable("materiales_docente/{codigo}") { backStackEntry ->
            val codigo = backStackEntry.arguments?.getString("codigo") ?: "DER-101"
            PantallaMaterialesPorUnidad(
                navController = navController,
                codigo = codigo
            )
        }

        //pantalla: PUBLICAR MATERIAL
        composable("publicar_material") {
            PantallaPublicarMaterial(navController)
        }
    }
}
