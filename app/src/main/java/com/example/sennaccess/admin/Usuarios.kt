package com.example.sennaccess.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.sennaccess.ui.theme.LocalAppColors
import com.example.sennaccess.ui.theme.SenaGreen

data class UsuarioMock(
    val nombre: String,
    val rol: String,
    val cc: String,
    val ficha: String,
    val programa: String,
    val jornada: String
)

private val instructores = listOf(
    UsuarioMock("Carlos Mendez", "Instructor", "79.123.456", "Ficha 2876541", "Análisis y Desarrollo de Software", "Mañana"),
    UsuarioMock("Laura Gomez", "Instructor", "52.987.654", "Ficha 2876542", "Gestión Empresarial", "Tarde"),
    UsuarioMock("Pedro Ramirez", "Instructor", "33.456.789", "Ficha 2876543", "Diseño Gráfico", "Noche")
)

private val aprendices = listOf(
    UsuarioMock("Juan Perez", "Aprendiz", "1.234.567", "Ficha 2876541", "ADSO", "Mañana"),
    UsuarioMock("Maria Lopez", "Aprendiz", "1.345.678", "Ficha 2876541", "ADSO", "Mañana"),
    UsuarioMock("Ana Torres", "Aprendiz", "1.456.789", "Ficha 2876542", "Gestión Empresarial", "Tarde"),
    UsuarioMock("Luis Castro", "Aprendiz", "1.567.890", "Ficha 2876543", "Diseño Gráfico", "Noche")
)

@Composable
fun Usuarios(
    onNavigate: (AdminScreen) -> Unit,
    onCerrarSesion: () -> Unit,
    onEditarUsuario: (Int, String) -> Unit,
    isDark: Boolean = true,
    onToggleTheme: () -> Unit = {}
) {
    val colors = LocalAppColors.current
    var busquedaInstructor by remember { mutableStateOf("") }
    var busquedaAprendiz by remember { mutableStateOf("") }
    var usuarioAEliminar by remember { mutableStateOf<UsuarioMock?>(null) }

    if (usuarioAEliminar != null) {
        EliminarUsuarioView(
            usuario = usuarioAEliminar!!,
            onConfirmar = { usuarioAEliminar = null; onNavigate(AdminScreen.PANEL) },
            onCancelar = { usuarioAEliminar = null },
            onCerrarSesion = onCerrarSesion,
            onNavigate = onNavigate,
            isDark = isDark,
            onToggleTheme = onToggleTheme
        )
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
                    Text("[INSTRUCTORES]", color = SenaGreen, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = busquedaInstructor,
                        onValueChange = { busquedaInstructor = it },
                        placeholder = { Text("Buscar instructor por nombre...", color = colors.textSecondary) },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = colors.textSecondary) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SenaGreen, unfocusedBorderColor = colors.borderLight,
                            cursorColor = SenaGreen, focusedTextColor = colors.textPrimary, unfocusedTextColor = colors.textPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(instructores) { instructor ->
                            TarjetaUsuario(instructor, onEditar = { onEditarUsuario(1, instructor.nombre) }, onBorrar = { usuarioAEliminar = instructor })
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                AdminGlassContainer {
                    Text("[APRENDICES]", color = SenaGreen, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = busquedaAprendiz,
                        onValueChange = { busquedaAprendiz = it },
                        placeholder = { Text("Buscar aprendiz por nombre...", color = colors.textSecondary) },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = colors.textSecondary) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SenaGreen, unfocusedBorderColor = colors.borderLight,
                            cursorColor = SenaGreen, focusedTextColor = colors.textPrimary, unfocusedTextColor = colors.textPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(aprendices) { aprendiz ->
                            TarjetaUsuario(aprendiz, onEditar = { onEditarUsuario(2, aprendiz.nombre) }, onBorrar = { usuarioAEliminar = aprendiz })
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedButton(
                    onClick = { onNavigate(AdminScreen.CREAR_USUARIO) },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, SenaGreen)
                ) { Text("+ AGREGAR NUEVO USUARIO", color = SenaGreen, fontWeight = FontWeight.Bold) }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun EliminarUsuarioView(
    usuario: UsuarioMock,
    onConfirmar: () -> Unit,
    onCancelar: () -> Unit,
    onCerrarSesion: () -> Unit,
    onNavigate: ((AdminScreen) -> Unit)? = null,
    isDark: Boolean = true,
    onToggleTheme: () -> Unit = {}
) {
    val colors = LocalAppColors.current
    AdminScreenLayout(
        onLogout = onCerrarSesion,
        onBack = onCancelar,
        onNavigate = onNavigate,
        isDark = isDark,
        onToggleTheme = onToggleTheme
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, RoundedCornerShape(12.dp))
                    .background(colors.cardBackground, RoundedCornerShape(12.dp))
                    .border(1.dp, colors.border, RoundedCornerShape(12.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.Delete, null, tint = Color.Red, modifier = Modifier.size(80.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Usuario Eliminado", color = SenaGreen, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                Spacer(modifier = Modifier.height(20.dp))
                detalLeUsuario(usuario)
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onConfirmar,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SenaGreen, contentColor = colors.textPrimary)
                ) { Text("Aceptar", fontWeight = FontWeight.Bold, fontSize = 16.sp) }
            }
        }
    }
}

@Composable
private fun detalLeUsuario(usuario: UsuarioMock) {
    val colors = LocalAppColors.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, colors.border, RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        filaDetalle("Nombre", usuario.nombre)
        filaDetalle("Rol", usuario.rol)
        filaDetalle("Cédula", usuario.cc)
        filaDetalle("Ficha", usuario.ficha)
        filaDetalle("Programa", usuario.programa)
        filaDetalle("Jornada", usuario.jornada)
    }
}

@Composable
private fun filaDetalle(label: String, valor: String) {
    val colors = LocalAppColors.current
    Row {
        Text("$label: ", color = SenaGreen, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(valor, color = colors.textPrimary, fontSize = 14.sp)
    }
}

@Composable
fun TarjetaUsuario(usuario: UsuarioMock, onEditar: () -> Unit, onBorrar: () -> Unit) {
    val colors = LocalAppColors.current
    Box(
        modifier = Modifier
            .width(220.dp)
            .shadow(10.dp, RoundedCornerShape(12.dp))
            .background(colors.cardBackground, RoundedCornerShape(12.dp))
            .border(1.dp, colors.border, RoundedCornerShape(12.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier.size(60.dp).clip(CircleShape).background(SenaGreen.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) { Icon(Icons.Default.Person, contentDescription = null, tint = SenaGreen, modifier = Modifier.size(40.dp)) }
            Spacer(modifier = Modifier.height(8.dp))
            Text(usuario.nombre, color = colors.textPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(usuario.rol, color = SenaGreen, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text("CC: ${usuario.cc}", color = colors.textSecondary, fontSize = 11.sp)
            Text(usuario.ficha, color = colors.textSecondary, fontSize = 11.sp)
            Text(usuario.programa, color = colors.textSecondary, fontSize = 11.sp)
            Text(usuario.jornada, color = colors.textSecondary, fontSize = 11.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = onEditar,
                    modifier = Modifier.height(32.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, SenaGreen)
                ) { Icon(Icons.Default.Edit, null, tint = SenaGreen, modifier = Modifier.size(16.dp)) }
                OutlinedButton(
                    onClick = onBorrar,
                    modifier = Modifier.height(32.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color.Red)
                ) { Icon(Icons.Default.Delete, null, tint = Color.Red, modifier = Modifier.size(16.dp)) }
            }
        }
    }
}
