
package com.example.esucar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.esucar.ui.screen.PantallaBienvenida
import com.example.esucar.ui.screen.PantallaLogin
import com.example.esucar.ui.theme.EsucarTheme


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
    val navController=rememberNavController()
    NavHost(
        navController= navController,
        startDestination = "bienvenida"
    ) {
        composable("bienvenida") {
            PantallaBienvenida(
                onIngresar={
                    navController.navigate("login")
                }
            )
        }
        composable("login") {
            PantallaLogin()
        }
    }
}
