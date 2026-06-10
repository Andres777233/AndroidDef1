package com.example.sennaccess.aprendiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.sennaccess.ui.theme.AppColors
import com.example.sennaccess.ui.theme.LocalAppColors
import com.example.sennaccess.ui.theme.SenaGreen

@Composable
fun AprendizDashboard(onCerrarSesion: () -> Unit, isDark: Boolean = true, onToggleTheme: () -> Unit = {}) {
    var currentView by rememberSaveable  { mutableStateOf("DASHBOARD") }
    val colors = LocalAppColors.current

    // Usamos Scaffold para anclar la barra de navegación abajo
    Scaffold(
        bottomBar = {
            AprendizBottomNav(
                currentView = currentView,
                onViewChange = { currentView = it }
            )
        },
        containerColor = colors.background,
        modifier = Modifier.systemBarsPadding()
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Respeta el espacio de la barra inferior
        ) {
            // 1. Patrón de fondo
            Image(
                painter = rememberAsyncImagePainter("https://www.sena.edu.co/Style%20Library/alayout/images/pattern.png"),
                contentDescription = null,
                modifier = Modifier.fillMaxSize().graphicsLayer(alpha = 0.15f),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.fillMaxSize()) {
                // 2. Barra Superior Simplificada
                AprendizTopBar(onLogout = onCerrarSesion, onPerfil = { currentView = "PERFIL" }, onToggleTheme = onToggleTheme)

                // 3. Contenido Dinámico
                Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    when (currentView) {
                        "DASHBOARD" -> ResumenView()
                        "HISTORIAL" -> HistorialView()
                        "COMPROBANTES" -> ComprobantesView()
                        "PERFIL" -> PerfilAprendizView(onBack = { currentView = "DASHBOARD" })
                    }
                }
            }
        }
    }
}

// --- BARRA INFERIOR (BOTTOM NAVIGATION) ---
@Composable
fun AprendizBottomNav(currentView: String, onViewChange: (String) -> Unit) {
    val colors = LocalAppColors.current
    NavigationBar(
        containerColor = colors.bottomNavBar,
        contentColor = SenaGreen,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = currentView == "DASHBOARD",
            onClick = { onViewChange("DASHBOARD") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio", fontSize = 10.sp) },
            colors = customNavColors(colors)
        )
        NavigationBarItem(
            selected = currentView == "HISTORIAL",
            onClick = { onViewChange("HISTORIAL") },
            icon = { Icon(Icons.Default.History, contentDescription = "Historial") },
            label = { Text("Historial", fontSize = 10.sp) },
            colors = customNavColors(colors)
        )
        NavigationBarItem(
            selected = currentView == "COMPROBANTES",
            onClick = { onViewChange("COMPROBANTES") },
            icon = { Icon(Icons.Default.Devices, contentDescription = "Equipos") },
            label = { Text("Equipos", fontSize = 10.sp) },
            colors = customNavColors(colors)
        )
    }
}

@Composable
fun customNavColors(colors: AppColors) = NavigationBarItemDefaults.colors(
    selectedIconColor = SenaGreen,
    selectedTextColor = SenaGreen,
    unselectedIconColor = colors.textSecondary,
    unselectedTextColor = colors.textSecondary,
    indicatorColor = SenaGreen.copy(alpha = 0.15f)
)

// --- BARRA SUPERIOR ---
@Composable
fun AprendizTopBar(onLogout: () -> Unit, onPerfil: (() -> Unit)? = null, onToggleTheme: () -> Unit = {}) {
    var showMenu by remember { mutableStateOf(false) }
    val colors = LocalAppColors.current

    Surface(
        color = colors.topBarBackground,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("SENA ", color = colors.textPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("ACCESS", color = SenaGreen, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.border(1.dp, SenaGreen.copy(0.5f), RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                    Text("APRENDIZ", color = SenaGreen, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onToggleTheme) {
                    Icon(
                        if (isSystemInDarkTheme()) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = null,
                        tint = colors.textPrimary
                    )
                }
                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.Menu, null, tint = colors.textPrimary)
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp).fillMaxWidth()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(contentAlignment = Alignment.BottomEnd) {
                                    Box(
                                        modifier = Modifier.size(50.dp).clip(CircleShape).background(SenaGreen.copy(alpha = 0.15f)),
                                        contentAlignment = Alignment.Center
                                    ) { Icon(Icons.Default.Person, null, tint = SenaGreen, modifier = Modifier.size(32.dp)) }
                                    Icon(Icons.Default.Edit, null, tint = SenaGreen, modifier = Modifier.size(14.dp))
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text("Pepito Perez", color = colors.textPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                    Text("pepito@sena.edu.co", color = colors.textSecondary, fontSize = 12.sp)
                                }
                            }
                        }
                        HorizontalDivider(color = colors.border)
                        if (onPerfil != null) {
                            DropdownMenuItem(
                                text = { Text("Perfil", color = colors.textPrimary) },
                                leadingIcon = { Icon(Icons.Default.Person, null, tint = SenaGreen) },
                                onClick = { showMenu = false; onPerfil() }
                            )
                        }
                        DropdownMenuItem(
                            text = { Text("Cerrar sesion", color = Color.Red) },
                            leadingIcon = { Icon(Icons.Default.Logout, null, tint = Color.Red) },
                            onClick = { showMenu = false; onLogout() }
                        )
                    }
                }
            }
        }
    }
}

// --- VISTA 1: DASHBOARD (RESUMEN) ---
@Composable
fun ResumenView() {
    val colors = LocalAppColors.current
    Column {
        Text("Dashboard Aprendiz", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = colors.textPrimary)
        Text("Bienvenido, pepito. Aquí tienes un resumen de tu actividad.", color = colors.textSecondary, fontSize = 13.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            StatCard("Mis Ingresos", "4", Icons.Default.History, Modifier.weight(1f))
            StatCard("Equipos Registrados", "0", Icons.Default.Devices, Modifier.weight(1f))
        }
    }
}

// --- VISTA 2: HISTORIAL DE ACCESOS ---
@Composable
fun HistorialView() {
    val colors = LocalAppColors.current
    TableContainer(title = "Mi Historial de Accesos", subtitle = "Registros de tus ingresos al centro de formación") {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
            Text("FECHA Y HORA", modifier = Modifier.width(180.dp), color = colors.textSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text("UBICACIÓN / PUNTO", modifier = Modifier.width(150.dp), color = colors.textSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text("ESTADO", modifier = Modifier.width(120.dp), color = colors.textSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }

        val historialItems = listOf(
            Triple("27/5/2026, 8:45:25 a. m.", "CCyS", "INGRESADO"),
            Triple("27/5/2026, 8:45:19 a. m.", "CCyS", "INGRESADO"),
            Triple("27/5/2026, 8:11:10 a. m.", "CCyS", "INGRESADO")
        )

        LazyColumn {
            items(historialItems) { item ->
                HorizontalDivider(color = colors.border)
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(item.first, modifier = Modifier.width(180.dp), color = colors.textPrimary, fontSize = 13.sp)
                    Box(modifier = Modifier.width(150.dp)) {
                        Text(item.second, color = SenaGreen, modifier = Modifier.border(1.dp, SenaGreen.copy(0.3f), RoundedCornerShape(4.dp)).padding(horizontal = 8.dp, vertical = 2.dp), fontSize = 11.sp)
                    }
                    Text("● ${item.third}", modifier = Modifier.width(120.dp), color = SenaGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// --- VISTA 3: COMPROBANTES DE EQUIPO ---
@Composable
fun ComprobantesView() {
    val colors = LocalAppColors.current
    TableContainer(title = "Mis Comprobantes de Equipo", subtitle = "Registros de tus dispositivos ingresados al centro") {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
            Text("EQUIPO", modifier = Modifier.width(100.dp), color = colors.textSecondary, fontSize = 12.sp)
            Text("MARCA/MODELO", modifier = Modifier.width(150.dp), color = colors.textSecondary, fontSize = 12.sp)
            Text("SERIAL", modifier = Modifier.width(120.dp), color = colors.textSecondary, fontSize = 12.sp)
            Text("FECHA DE INGRESO", modifier = Modifier.width(160.dp), color = colors.textSecondary, fontSize = 12.sp)
            Text("ESTADO", modifier = Modifier.width(100.dp), color = colors.textSecondary, fontSize = 12.sp)
        }
        HorizontalDivider(color = colors.border)
        Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
            Text("No tienes equipos registrados.", color = colors.textSecondary, fontSize = 14.sp)
        }
    }
}

// --- COMPONENTES REUTILIZABLES ---

@Composable
fun StatCard(label: String, value: String, icon: ImageVector, modifier: Modifier) {
    val colors = LocalAppColors.current
    Box(
        modifier = modifier
            .shadow(10.dp, RoundedCornerShape(12.dp))
            .background(colors.cardBackground, RoundedCornerShape(12.dp))
            .border(1.dp, colors.border, RoundedCornerShape(12.dp))
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = SenaGreen, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(label, color = colors.textSecondary, fontSize = 12.sp)
                Text(value, color = colors.textPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TableContainer(title: String, subtitle: String, content: @Composable ColumnScope.() -> Unit) {
    val colors = LocalAppColors.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(20.dp, RoundedCornerShape(16.dp))
            .background(colors.cardBackground, RoundedCornerShape(16.dp))
            .border(1.dp, colors.borderLight, RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Text(title, color = colors.textPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(subtitle, color = colors.subtitleText, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            Column(modifier = Modifier.widthIn(min = 600.dp)) {
                content()
            }
        }
    }
}

@Composable
fun PerfilAprendizView(onBack: () -> Unit) {
    val colors = LocalAppColors.current
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint = colors.textPrimary) }
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(10.dp, RoundedCornerShape(12.dp))
                .background(colors.cardBackground, RoundedCornerShape(12.dp))
                .border(1.dp, colors.border, RoundedCornerShape(12.dp))
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier.size(80.dp).clip(CircleShape).background(SenaGreen.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) { Icon(Icons.Default.Person, null, tint = SenaGreen, modifier = Modifier.size(50.dp)) }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Pepito Perez", color = colors.textPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Aprendiz", color = SenaGreen, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(24.dp))
                filaPerfil("Correo", "pepito@sena.edu.co", colors)
                filaPerfil("Documento", "1.234.567", colors)
                filaPerfil("Ficha", "2876541", colors)
                filaPerfil("Programa", "ADSO", colors)
                filaPerfil("Jornada", "Manana", colors)
            }
        }
    }
}

@Composable
private fun filaPerfil(label: String, valor: String, colors: AppColors) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Text("$label: ", color = SenaGreen, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(valor, color = colors.textPrimary, fontSize = 14.sp)
    }
}