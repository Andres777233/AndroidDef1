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
fun ActualizarUsuario(
    usuarioId: Int,
    usuarioNombre: String,
    onNavigate: (AdminScreen) -> Unit,
    onCerrarSesion: () -> Unit,
    isDark: Boolean = true,
    onToggleTheme: () -> Unit = {}
) {
    var correo by remember { mutableStateOf("") }
    var tipoId by remember { mutableStateOf("") }
    var numeroId by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    var actualizado by remember { mutableStateOf(false) }
    val colors = LocalAppColors.current

    if (actualizado) {
        AdminScreenLayout(
            onLogout = onCerrarSesion,
            onBack = { actualizado = false },
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
                    Text("¡Actualización Exitosa!", color = SenaGreen, fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Los datos de $usuarioNombre han sido actualizados correctamente.",
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
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            ) {
                AdminGlassContainer {
                    Text("Actualizar Datos Del Aprendiz", color = colors.textPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            OutlinedTextField(value = correo, onValueChange = { correo = it }, label = { Text("Correo Electronico") }, modifier = Modifier.fillMaxWidth(), colors = campoColors())
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(value = tipoId, onValueChange = { tipoId = it }, label = { Text("Tipo De Identificacion") }, modifier = Modifier.fillMaxWidth(), colors = campoColors())
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(value = numeroId, onValueChange = { numeroId = it }, label = { Text("Numero De Identificacion") }, modifier = Modifier.fillMaxWidth(), colors = campoColors())
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            OutlinedTextField(value = direccion, onValueChange = { direccion = it }, label = { Text("Direccion") }, modifier = Modifier.fillMaxWidth(), colors = campoColors())
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(value = telefono, onValueChange = { telefono = it }, label = { Text("Telefono") }, modifier = Modifier.fillMaxWidth(), colors = campoColors())
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(value = pin, onValueChange = { pin = it }, label = { Text("PIN") }, modifier = Modifier.fillMaxWidth(), colors = campoColors())
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedButton(
                            onClick = { onNavigate(AdminScreen.PANEL) },
                            modifier = Modifier.weight(1f).height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, colors.textSecondary)
                        ) { Text("CANCELAR", color = colors.textSecondary, fontWeight = FontWeight.Bold) }
                        Button(
                            onClick = { actualizado = true },
                            modifier = Modifier.weight(1f).height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SenaGreen, contentColor = colors.textPrimary)
                        ) { Text("ACTUALIZAR", fontWeight = FontWeight.Bold) }
                    }
                }
            }
        }
    }
}

@Composable
private fun campoColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = SenaGreen, unfocusedBorderColor = LocalAppColors.current.textSecondary,
    focusedLabelColor = SenaGreen, unfocusedLabelColor = LocalAppColors.current.textSecondary,
    cursorColor = SenaGreen, focusedTextColor = LocalAppColors.current.textPrimary, unfocusedTextColor = LocalAppColors.current.textPrimary
)
