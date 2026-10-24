package com.example.esucar.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PieInstitucional() {
    Text(
        text = "Carabineros de Chile · Subsistema Técnico Profesional",
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        fontSize = 10.sp,
        color = Color(0xFF718078),
        textAlign = TextAlign.Center
    )
}
