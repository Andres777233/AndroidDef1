package com.example.sennaccess.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sennaccess.ui.theme.LocalAppColors
import com.example.sennaccess.ui.theme.SenaGreen

@Composable
fun Perfil(
    onNavigate: (AdminScreen) -> Unit,
    onCerrarSesion: () -> Unit,
    isDark: Boolean = true,
    onToggleTheme: () -> Unit = {}
) {
    val colors = LocalAppColors.current
    var nombre by remember { mutableStateOf("Juan Perez") }
    var email by remember { mutableStateOf("juan.perez@sena.edu.co") }
    var passNueva by remember { mutableStateOf("") }
    var passConfirm by remember { mutableStateOf("") }

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
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .shadow(15.dp, CircleShape, spotColor = SenaGreen, ambientColor = SenaGreen.copy(alpha = 0.3f))
                    .clip(CircleShape)
                    .background(SenaGreen.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) { Icon(Icons.Default.Person, contentDescription = null, tint = SenaGreen, modifier = Modifier.size(70.dp)) }
            Spacer(modifier = Modifier.height(8.dp))
            Text(nombre, color = colors.textPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Administrador", color = SenaGreen, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(24.dp))
            SeccionPerfil("Informacion Personal y Seguridad") {
                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth(), colors = perfilColors())
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth(), colors = perfilColors())
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(value = passNueva, onValueChange = { passNueva = it }, label = { Text("Nueva contrasena") }, modifier = Modifier.fillMaxWidth(), colors = perfilColors())
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(value = passConfirm, onValueChange = { passConfirm = it }, label = { Text("Confirmar contrasena") }, modifier = Modifier.fillMaxWidth(), colors = perfilColors())
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(45.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SenaGreen, contentColor = colors.textPrimary)
                ) { Text("Guardar cambios", fontWeight = FontWeight.Bold) }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SeccionPerfil(titulo: String, content: @Composable ColumnScope.() -> Unit) {
    val colors = LocalAppColors.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(10.dp, RoundedCornerShape(12.dp))
            .background(colors.cardBackground, RoundedCornerShape(12.dp))
            .border(1.dp, colors.border, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(titulo, color = colors.textPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(12.dp))
        content()
    }
}

@Composable
private fun perfilColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = SenaGreen, unfocusedBorderColor = LocalAppColors.current.textSecondary,
    focusedLabelColor = SenaGreen, unfocusedLabelColor = LocalAppColors.current.textSecondary,
    cursorColor = SenaGreen, focusedTextColor = LocalAppColors.current.textPrimary, unfocusedTextColor = LocalAppColors.current.textPrimary
)
