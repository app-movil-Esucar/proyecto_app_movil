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
        //pantalla: BIENVENIDA
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
            PantallaRegistro()
        }
        //pantalla: INICIO EST.
        composable("inicio_estudiante") {
            PantallaInicioEstudiante(navController)
        }

        //pantalla: ASIGNATURAS
        composable("mis_asignaturas") {
            PantallaMisAsignaturas(navController)
        }
        //pantala: DETALLES ASIGNATURA
        composable("detalle_asignatura/{codigo}") { backStackEntry ->
            val codigo = backStackEntry.arguments
                ?.getString("codigo") ?: ""

            PantallaDetalleAsignatura(
                navController = navController,
                codigo = codigo
            )
        }
        //pantalla: DETALLE MATERIALES


        composable(
            "detalle_material/{nombreMaterial}?codigo={codigo}&unidad={unidad}"
        ) { backStackEntry ->
            val nombreMaterial = backStackEntry.arguments
                ?.getString("nombreMaterial") ?: ""
            val codigo = backStackEntry.arguments
                ?.getString("codigo") ?: ""
            val unidad = backStackEntry.arguments
                ?.getString("unidad") ?: ""

            PantallaDetalleMaterial(
                navController = navController,
                nombreMaterial = nombreMaterial,
                codigo = codigo,
                unidad = unidad
            )
        }

        //pantalla: CONSULTA ESTUDIANTE
        composable("consultas_estudiante") {
            PantallaMisConsultas(navController)
        }

        //pantalla: NUEVA CONSULTA
        composable(
            "nueva_consulta?codigo={codigo}&unidad={unidad}&material={material}"
        ) { backStackEntry ->
            val codigo = backStackEntry.arguments
                ?.getString("codigo") ?: ""
            val unidad = backStackEntry.arguments
                ?.getString("unidad") ?: ""
            val material = backStackEntry.arguments
                ?.getString("material") ?: ""
            PantallaNuevaConsulta(
                navController = navController,
                codigo = codigo,
                unidad = unidad,
                material = material
            )
        }








    }
}
