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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sennaccess.ui.theme.LocalAppColors
import com.example.sennaccess.ui.theme.SenaGreen

@Composable
fun CrearUsuario(
    onNavigate: (AdminScreen) -> Unit,
    onCerrarSesion: () -> Unit,
    isDark: Boolean = true,
    onToggleTheme: () -> Unit = {}
) {
    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var identificacion by remember { mutableStateOf("") }
    var programa by remember { mutableStateOf("") }
    var ficha by remember { mutableStateOf("") }
    var jornada by remember { mutableStateOf("") }
    var creado by remember { mutableStateOf(false) }

    val colors = LocalAppColors.current
    if (creado) {
        AdminScreenLayout(
            onLogout = onCerrarSesion,
            onBack = { creado = false },
            onNavigate = onNavigate,
            isDark = isDark,
            onToggleTheme = onToggleTheme
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .shadow(10.dp, RoundedCornerShape(12.dp))
                        .background(colors.cardBackground, RoundedCornerShape(12.dp))
                        .border(1.dp, SenaGreen.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.CheckCircle, null, tint = SenaGreen, modifier = Modifier.size(80.dp))
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("¡Usuario Creado!", color = SenaGreen, fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "El usuario ha sido creado exitosamente.",
                        color = colors.textPrimary, fontSize = 18.sp, textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { onNavigate(AdminScreen.PANEL) },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SenaGreen, contentColor = colors.textPrimary)
                    ) { Text("Volver al panel", fontWeight = FontWeight.Bold, fontSize = 16.sp) }
                }
            }
        }
    } else {
        AdminScreenLayout(
            onLogout = onCerrarSesion,
            onBack = { onNavigate(AdminScreen.PANEL) },
            onNavigate = onNavigate,
            isDark = isDark,
            onToggleTheme = onToggleTheme
        ) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AdminGlassContainer {
                    Text("Nuevo Usuario", color = colors.textPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally))
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(value = nombres, onValueChange = { nombres = it }, label = { Text("Nombres") }, modifier = Modifier.weight(1f), colors = campoCrearColors())
                        OutlinedTextField(value = apellidos, onValueChange = { apellidos = it }, label = { Text("Apellidos") }, modifier = Modifier.weight(1f), colors = campoCrearColors())
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(value = correo, onValueChange = { correo = it }, label = { Text("Correo Electronico") }, modifier = Modifier.weight(1f), colors = campoCrearColors())
                        OutlinedTextField(value = identificacion, onValueChange = { identificacion = it }, label = { Text("Numero de Identificacion") }, modifier = Modifier.weight(1f), colors = campoCrearColors())
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(value = programa, onValueChange = { programa = it }, label = { Text("Programa de Formacion") }, modifier = Modifier.weight(1f), colors = campoCrearColors())
                        OutlinedTextField(value = ficha, onValueChange = { ficha = it }, label = { Text("Ficha") }, modifier = Modifier.weight(1f), colors = campoCrearColors())
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(value = jornada, onValueChange = { jornada = it }, label = { Text("Jornada") }, modifier = Modifier.fillMaxWidth(), colors = campoCrearColors())
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { creado = true },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SenaGreen, contentColor = colors.textPrimary)
                    ) { Text("Crear", fontWeight = FontWeight.Bold, fontSize = 16.sp) }
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = { onNavigate(AdminScreen.PANEL) },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, colors.textSecondary)
                    ) { Text("Cancelar", color = colors.textSecondary, fontWeight = FontWeight.Bold) }
                }
            }
        }
    }
}

@Composable
private fun campoCrearColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = SenaGreen, unfocusedBorderColor = LocalAppColors.current.textSecondary,
    focusedLabelColor = SenaGreen, unfocusedLabelColor = LocalAppColors.current.textSecondary,
    cursorColor = SenaGreen, focusedTextColor = LocalAppColors.current.textPrimary, unfocusedTextColor = LocalAppColors.current.textPrimary
)
