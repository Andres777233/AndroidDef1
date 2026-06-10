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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sennaccess.ui.theme.LocalAppColors
import com.example.sennaccess.ui.theme.SenaGreen

@Composable
fun ReporteInstructor(
    onNavigate: (AdminScreen) -> Unit,
    onCerrarSesion: () -> Unit,
    isDark: Boolean = true,
    onToggleTheme: () -> Unit = {}
) {
    val colors = LocalAppColors.current
    var usuario by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var asunto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

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
            AdminGlassContainer {
                Text("Reporte Al Instructor", color = colors.textPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
                OutlinedTextField(
                    value = usuario, onValueChange = { usuario = it },
                    label = { Text("Usuario") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = campoRepInsColors()
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = fecha, onValueChange = { fecha = it },
                        label = { Text("Fecha") },
                        modifier = Modifier.weight(1f),
                        colors = campoRepInsColors()
                    )
                    OutlinedTextField(
                        value = hora, onValueChange = { hora = it },
                        label = { Text("Hora") },
                        modifier = Modifier.weight(1f),
                        colors = campoRepInsColors()
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = asunto, onValueChange = { asunto = it },
                    label = { Text("Asunto") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = campoRepInsColors()
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = descripcion, onValueChange = { descripcion = it },
                    label = { Text("Descripcion") },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    colors = campoRepInsColors()
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SenaGreen, contentColor = colors.textPrimary)
                ) { Text("Enviar", fontWeight = FontWeight.Bold, fontSize = 16.sp) }
            }
        }
    }
}

@Composable
private fun campoRepInsColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = SenaGreen, unfocusedBorderColor = LocalAppColors.current.textSecondary,
    focusedLabelColor = SenaGreen, unfocusedLabelColor = LocalAppColors.current.textSecondary,
    cursorColor = SenaGreen, focusedTextColor = LocalAppColors.current.textPrimary, unfocusedTextColor = LocalAppColors.current.textPrimary
)
