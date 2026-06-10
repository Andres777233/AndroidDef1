package com.example.sennaccess.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.sennaccess.ui.theme.AppColors
import com.example.sennaccess.ui.theme.LocalAppColors
import com.example.sennaccess.ui.theme.SenaGreen

enum class AdminScreen { PANEL, USUARIOS, CREAR_USUARIO, ACTUALIZAR_USUARIO,
    ACCESO_APRENDICES, ACCESO_INSTRUCTORES, REPORTE_NOVEDADES, REPORTE_INSTRUCTOR, PERFIL, MENSAJE }

data class AccesoReciente(
    val nombre: String,
    val rol: String,
    val tipo: String,
    val hora: String
)

private val accesosRecientes = listOf(
    AccesoReciente("Juan Perez", "Aprendiz", "Entrada", "07:45 AM"),
    AccesoReciente("Maria Lopez", "Aprendiz", "Salida", "12:30 PM"),
    AccesoReciente("Carlos Mendez", "Instructor", "Entrada", "07:15 AM"),
    AccesoReciente("Ana Torres", "Aprendiz", "Entrada", "08:00 AM"),
    AccesoReciente("Pedro Ramirez", "Instructor", "Salida", "01:00 PM"),
)

@Composable
fun PanelPrincipal(
    onNavigate: (AdminScreen) -> Unit,
    onCerrarSesion: () -> Unit,
    isDark: Boolean = true,
    onToggleTheme: () -> Unit = {}
) {
    val colors = LocalAppColors.current

    Scaffold(
        bottomBar = {
            AdminBottomNav(onNavigate = onNavigate)
        },
        containerColor = colors.background,
        modifier = Modifier.systemBarsPadding()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = rememberAsyncImagePainter("https://www.sena.edu.co/Style%20Library/alayout/images/pattern.png"),
                contentDescription = null,
                modifier = Modifier.fillMaxSize().graphicsLayer(alpha = 0.15f),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.fillMaxSize()) {
                AdminTopBar(onLogout = onCerrarSesion, onNavigate = onNavigate, isDark = isDark, onToggleTheme = onToggleTheme)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        "Accesos Recientes",
                        color = colors.textPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    accesosRecientes.forEachIndexed { index, acceso ->
                        TarjetaAccesoReciente(acceso)
                        if (index < accesosRecientes.lastIndex) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminBottomNav(onNavigate: (AdminScreen) -> Unit) {
    val colors = LocalAppColors.current
    NavigationBar(
        containerColor = colors.bottomNavBar,
        contentColor = SenaGreen,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio", fontSize = 10.sp) },
            colors = adminNavColors(colors)
        )
        NavigationBarItem(
            selected = false,
            onClick = { onNavigate(AdminScreen.ACCESO_APRENDICES) },
            icon = { Icon(Icons.Default.VpnKey, contentDescription = "Acceso") },
            label = { Text("Acceso", fontSize = 10.sp) },
            colors = adminNavColors(colors)
        )
        NavigationBarItem(
            selected = false,
            onClick = { onNavigate(AdminScreen.REPORTE_NOVEDADES) },
            icon = { Icon(Icons.Default.WarningAmber, contentDescription = "Novedades") },
            label = { Text("Novedades", fontSize = 10.sp) },
            colors = adminNavColors(colors)
        )
        NavigationBarItem(
            selected = false,
            onClick = { onNavigate(AdminScreen.REPORTE_INSTRUCTOR) },
            icon = { Icon(Icons.Default.AssignmentInd, contentDescription = "Instructor") },
            label = { Text("Instructor", fontSize = 10.sp) },
            colors = adminNavColors(colors)
        )
        NavigationBarItem(
            selected = false,
            onClick = { onNavigate(AdminScreen.USUARIOS) },
            icon = { Icon(Icons.Default.People, contentDescription = "Usuarios") },
            label = { Text("Usuarios", fontSize = 10.sp) },
            colors = adminNavColors(colors)
        )
    }
}

@Composable
fun adminNavColors(colors: AppColors) = NavigationBarItemDefaults.colors(
    selectedIconColor = SenaGreen,
    selectedTextColor = SenaGreen,
    unselectedIconColor = colors.textSecondary,
    unselectedTextColor = colors.textSecondary,
    indicatorColor = SenaGreen.copy(alpha = 0.15f)
)

@Composable
fun TarjetaAccesoReciente(acceso: AccesoReciente) {
    val colors = LocalAppColors.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(10.dp, RoundedCornerShape(12.dp))
            .background(colors.cardBackground, RoundedCornerShape(12.dp))
            .border(1.dp, colors.border, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(SenaGreen.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (acceso.rol == "Instructor") Icons.Default.AssignmentInd else Icons.Default.Person,
                    contentDescription = null,
                    tint = SenaGreen,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(acceso.nombre, color = colors.textPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Row {
                    Text(acceso.rol, color = SenaGreen, fontSize = 11.sp)
                    Text(" | ${acceso.tipo}", color = colors.textSecondary, fontSize = 11.sp)
                }
            }
            Text(acceso.hora, color = colors.textSecondary, fontSize = 12.sp)
        }
    }
}

@Composable
fun TarjetaPerfilPanel(modifier: Modifier = Modifier) {
    val colors = LocalAppColors.current
    Box(
        modifier = modifier
            .shadow(10.dp, RoundedCornerShape(12.dp))
            .background(colors.cardBackground, RoundedCornerShape(12.dp))
            .border(1.dp, colors.border, RoundedCornerShape(12.dp))
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .border(2.dp, SenaGreen, CircleShape)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = colors.textSecondary, modifier = Modifier.size(38.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("ADMINISTRADOR", color = colors.textPrimary, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text("admin@sena.edu.co", color = colors.textSecondary, fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun BotonPanel(texto: String, icono: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val colors = LocalAppColors.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .shadow(10.dp, RoundedCornerShape(12.dp))
            .background(colors.cardBackground, RoundedCornerShape(12.dp))
            .border(1.dp, colors.border, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icono, contentDescription = null, tint = SenaGreen, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(texto, color = SenaGreen, fontSize = 14.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }
    }
}
