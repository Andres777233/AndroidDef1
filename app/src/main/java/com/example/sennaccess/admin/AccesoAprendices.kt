package com.example.sennaccess.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sennaccess.ui.theme.LocalAppColors
import com.example.sennaccess.ui.theme.SenaGreen

data class RegistroAccesoMock(
    val nombre: String,
    val fecha: String,
    val hora: String,
    val ficha: String,
    val documento: String
)

private val registros = listOf(
    RegistroAccesoMock("Juan Perez", "2026-05-19", "08:30", "Ficha 2876541", "1.234.567"),
    RegistroAccesoMock("Maria Lopez", "2026-05-19", "09:15", "Ficha 2876541", "1.345.678"),
    RegistroAccesoMock("Ana Torres", "2026-05-19", "10:00", "Ficha 2876542", "1.456.789"),
    RegistroAccesoMock("Luis Castro", "2026-05-19", "11:30", "Ficha 2876543", "1.567.890")
)

@Composable
fun AccesoAprendices(
    onNavigate: (AdminScreen) -> Unit,
    onCerrarSesion: () -> Unit,
    isDark: Boolean = true,
    onToggleTheme: () -> Unit = {}
) {
    val colors = LocalAppColors.current
    AdminScreenLayout(
        onLogout = onCerrarSesion,
        onBack = { onNavigate(AdminScreen.PANEL) },
        onNavigate = onNavigate,
        isDark = isDark,
        onToggleTheme = onToggleTheme
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
        ) {
            registros.forEach { r ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .shadow(10.dp, RoundedCornerShape(12.dp))
                        .background(colors.cardBackground, RoundedCornerShape(12.dp))
                        .border(1.dp, colors.border, RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = SenaGreen, modifier = Modifier.size(40.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(r.nombre, color = colors.textPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text("${r.fecha} - ${r.hora}", color = colors.textSecondary, fontSize = 12.sp)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(r.ficha, color = SenaGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text("Aprendiz", color = SenaGreen.copy(alpha = 0.7f), fontSize = 11.sp)
                            Text("ID: ${r.documento}", color = colors.textSecondary, fontSize = 10.sp)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = { onNavigate(AdminScreen.ACCESO_INSTRUCTORES) }) {
                Text("Ver registro de instructores >", color = SenaGreen)
            }
        }
    }
}
