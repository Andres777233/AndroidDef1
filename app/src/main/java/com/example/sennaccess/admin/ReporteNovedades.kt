package com.example.sennaccess.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sennaccess.ui.theme.ErrorRed
import com.example.sennaccess.ui.theme.LocalAppColors
import com.example.sennaccess.ui.theme.SenaGreen

@Composable
fun ReporteNovedades(
    onNavigate: (AdminScreen) -> Unit,
    onCerrarSesion: () -> Unit,
    isDark: Boolean = true,
    onToggleTheme: () -> Unit = {}
) {
    val colors = LocalAppColors.current
    var elemento by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var accesorio by remember { mutableStateOf("") }
    var propietario by remember { mutableStateOf("") }
    var admin by remember { mutableStateOf("") }

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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .shadow(10.dp, RoundedCornerShape(12.dp))
                    .background(colors.cardBackground, RoundedCornerShape(12.dp))
                    .border(1.dp, colors.border, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) { CampoReporte("Elemento", elemento, { elemento = it }) }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .shadow(10.dp, RoundedCornerShape(12.dp))
                    .background(colors.cardBackground, RoundedCornerShape(12.dp))
                    .border(1.dp, colors.border, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(value = fecha, onValueChange = { fecha = it }, label = { Text("Fecha") }, modifier = Modifier.weight(1f), colors = campoRepColors())
                    OutlinedTextField(value = hora, onValueChange = { hora = it }, label = { Text("Hora") }, modifier = Modifier.weight(1f), colors = campoRepColors())
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .shadow(10.dp, RoundedCornerShape(12.dp))
                    .background(colors.cardBackground, RoundedCornerShape(12.dp))
                    .border(1.dp, colors.border, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) { CampoReporte("Accesorio Adicional", accesorio, { accesorio = it }) }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .shadow(10.dp, RoundedCornerShape(12.dp))
                    .background(colors.cardBackground, RoundedCornerShape(12.dp))
                    .border(1.dp, colors.border, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) { CampoReporte("Propietario", propietario, { propietario = it }) }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .shadow(10.dp, RoundedCornerShape(12.dp))
                    .background(colors.cardBackground, RoundedCornerShape(12.dp))
                    .border(1.dp, colors.border, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) { CampoReporte("Administrador que Registra", admin, { admin = it }) }
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, RoundedCornerShape(12.dp))
                    .background(colors.errorBackground, RoundedCornerShape(12.dp))
                    .border(1.dp, ErrorRed.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text(
                    "AVISO - El Centro De Servicio Y Comercio no se hace responsable por objetos de valor no reportados en este comprobante.",
                    color = ErrorRed, fontSize = 12.sp, textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SenaGreen, contentColor = colors.textPrimary)
            ) { Text("Enviar", fontWeight = FontWeight.Bold, fontSize = 16.sp) }
        }
    }
}

@Composable
private fun CampoReporte(label: String, value: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value, onValueChange = onChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        colors = campoRepColors()
    )
}

@Composable
private fun campoRepColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = SenaGreen, unfocusedBorderColor = LocalAppColors.current.textSecondary,
    focusedLabelColor = SenaGreen, unfocusedLabelColor = LocalAppColors.current.textSecondary,
    cursorColor = SenaGreen, focusedTextColor = LocalAppColors.current.textPrimary, unfocusedTextColor = LocalAppColors.current.textPrimary
)
