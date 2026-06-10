package com.example.sennaccess.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import com.example.sennaccess.ui.theme.LocalAppColors
import com.example.sennaccess.ui.theme.SenaGreen

@Composable
fun InstructorDashboard(onCerrarSesion: () -> Unit, isDark: Boolean = true, onToggleTheme: () -> Unit = {}) {
    var currentView by rememberSaveable  { mutableStateOf("DASHBOARD") }
    val colors = LocalAppColors.current

    Scaffold(
        bottomBar = {
            InstructorBottomNav(
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
                .padding(paddingValues)
        ) {
            Image(
                painter = rememberAsyncImagePainter("https://www.sena.edu.co/Style%20Library/alayout/images/pattern.png"),
                contentDescription = null,
                modifier = Modifier.fillMaxSize().graphicsLayer(alpha = 0.15f),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.fillMaxSize()) {
                InstructorTopBar(onLogout = onCerrarSesion, onPerfil = { currentView = "PERFIL" }, isDark = isDark, onToggleTheme = onToggleTheme)

                Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    when (currentView) {
                        "DASHBOARD" -> InstructorResumenView()
                        "NOVEDADES" -> NovedadesPlaceholder("Sección de Novedades")
                        "HISTORIAL" -> HistorialIngresosView()
                        "MIS_EQUIPOS" -> MisEquiposView()
                        "PERFIL" -> PerfilInstructorView(onBack = { currentView = "DASHBOARD" })
                    }
                }
            }
        }
    }
}

@Composable
fun InstructorBottomNav(currentView: String, onViewChange: (String) -> Unit) {
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
            colors = customNavColors()
        )
        NavigationBarItem(
            selected = currentView == "NOVEDADES",
            onClick = { onViewChange("NOVEDADES") },
            icon = { Icon(Icons.Default.ReportProblem, contentDescription = "Novedades") },
            label = { Text("Novedades", fontSize = 10.sp) },
            colors = customNavColors()
        )
        NavigationBarItem(
            selected = currentView == "HISTORIAL",
            onClick = { onViewChange("HISTORIAL") },
            icon = { Icon(Icons.Default.History, contentDescription = "Historial") },
            label = { Text("Historial", fontSize = 10.sp) },
            colors = customNavColors()
        )
        NavigationBarItem(
            selected = currentView == "MIS_EQUIPOS",
            onClick = { onViewChange("MIS_EQUIPOS") },
            icon = { Icon(Icons.Default.Devices, contentDescription = "Equipos") },
            label = { Text("Equipos", fontSize = 10.sp) },
            colors = customNavColors()
        )
    }
}

@Composable
fun customNavColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = SenaGreen,
    selectedTextColor = SenaGreen,
    unselectedIconColor = LocalAppColors.current.textSecondary,
    unselectedTextColor = LocalAppColors.current.textSecondary,
    indicatorColor = SenaGreen.copy(alpha = 0.15f)
)

@Composable
fun InstructorTopBar(onLogout: () -> Unit, onPerfil: (() -> Unit)? = null, isDark: Boolean, onToggleTheme: () -> Unit) {
    var showMenu by remember { mutableStateOf(false) }
    val colors = LocalAppColors.current

    Surface(color = colors.topBarBackground, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("SENA ", color = colors.textPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("ACCESS", color = SenaGreen, fontWeight = FontWeight.Bold, fontSize = 18.sp)

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .border(1.dp, SenaGreen.copy(0.6f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "INSTRUCTOR",
                        color = SenaGreen,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onToggleTheme) {
                    Icon(
                        if (isDark) Icons.Default.WbSunny else Icons.Default.DarkMode,
                        contentDescription = "Toggle theme",
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
                                    Text("Carlos Mendez", color = colors.textPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                    Text("carlos.mendez@sena.edu.co", color = colors.textSecondary, fontSize = 12.sp)
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

@Composable
fun InstructorResumenView() {
    val colors = LocalAppColors.current
    Column {
        Text("Panel de Instructor", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = colors.textPrimary)
        Text("Visualización y gestión de usuarios y registros de acceso", color = colors.textSecondary, fontSize = 13.sp)
        Spacer(modifier = Modifier.height(32.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            StatCard("Usuarios", "156", Icons.Default.Group, Modifier.weight(1f))
            StatCard("Ingresos", "89", Icons.Default.Login, Modifier.weight(1f))
        }
    }
}

@Composable
fun UsuariosGestionView(filter: String) {
    val colors = LocalAppColors.current
    var isCreating by remember { mutableStateOf(false) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Gestión de Usuarios", color = colors.textPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(if (filter == "all") "Todos los registros" else "Filtro: $filter", color = SenaGreen, fontSize = 12.sp)
                }
                Button(
                    onClick = { isCreating = !isCreating },
                    colors = ButtonDefaults.buttonColors(containerColor = SenaGreen, contentColor = Color.Black)
                ) {
                    Icon(Icons.Default.PersonAdd, null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (isCreating) "Cancelar" else "Nuevo", fontWeight = FontWeight.Bold)
                }
            }
        }
        if (isCreating) { item { MockUserForm(onCancel = { isCreating = false }) } }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                MockUserCard("Ana Martínez", "Aprendiz", "1002345678", "ana@sena.edu.co")
                MockUserCard("Carlos Gómez", "Instructor", "987654321", "carlos@sena.edu.co")
            }
        }
    }
}

@Composable
fun HistorialIngresosView() {
    val colors = LocalAppColors.current
    TableContainer(title = "Control de Ingresos", subtitle = "Supervisión general de accesos") {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
            Text("USUARIO", modifier = Modifier.width(160.dp), color = colors.textSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text("FECHA Y HORA", modifier = Modifier.width(160.dp), color = colors.textSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text("UBICACIÓN", modifier = Modifier.width(100.dp), color = colors.textSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        HorizontalDivider(color = colors.border)
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("Juan Pérez", modifier = Modifier.width(160.dp), color = colors.textPrimary, fontSize = 13.sp)
            Text("27/5/2026, 8:45 a.m.", modifier = Modifier.width(160.dp), color = colors.textPrimary, fontSize = 13.sp)
            Text("CCyS", modifier = Modifier.width(100.dp), color = SenaGreen, fontSize = 12.sp)
        }
    }
}

@Composable
fun MisEquiposView() {
    val colors = LocalAppColors.current
    TableContainer(title = "Mis Comprobantes", subtitle = "Dispositivos del instructor") {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
            Text("EQUIPO", modifier = Modifier.width(100.dp), color = colors.textSecondary, fontSize = 12.sp)
            Text("MARCA/MODELO", modifier = Modifier.width(150.dp), color = colors.textSecondary, fontSize = 12.sp)
            Text("SERIAL", modifier = Modifier.width(120.dp), color = colors.textSecondary, fontSize = 12.sp)
        }
        HorizontalDivider(color = colors.border)
        Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
            Text("No hay equipos registrados.", color = colors.textSecondary, fontSize = 14.sp)
        }
    }
}

@Composable
fun NovedadesPlaceholder(title: String) {
    val colors = LocalAppColors.current
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.ReportProblem, null, tint = SenaGreen, modifier = Modifier.size(64.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text(title, color = colors.textPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun MockUserForm(onCancel: () -> Unit) {
    val colors = LocalAppColors.current
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp).shadow(15.dp, RoundedCornerShape(12.dp)).background(colors.cardBackground, RoundedCornerShape(12.dp)).border(1.dp, SenaGreen.copy(0.3f), RoundedCornerShape(12.dp)).padding(16.dp)
    ) {
        Text("Nuevo Usuario", color = colors.textPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Nombre Completo") }, modifier = Modifier.fillMaxWidth(), colors = customTextFieldColors())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Email Institucional") }, modifier = Modifier.fillMaxWidth(), colors = customTextFieldColors())
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            Button(onClick = onCancel, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = SenaGreen, contentColor = Color.Black)) { Text("Guardar") }
            OutlinedButton(onClick = onCancel, modifier = Modifier.weight(1f), border = BorderStroke(1.dp, colors.textSecondary)) { Text("Cancelar", color = colors.textPrimary) }
        }
    }
}

@Composable
fun MockUserCard(nombre: String, rol: String, doc: String, email: String) {
    val colors = LocalAppColors.current
    Row(
        modifier = Modifier.fillMaxWidth().background(colors.topBarBackground, RoundedCornerShape(12.dp)).border(1.dp, colors.border, RoundedCornerShape(12.dp)).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(50.dp).clip(CircleShape).background(SenaGreen.copy(0.2f)), contentAlignment = Alignment.Center) {
            Text(nombre.take(1), color = SenaGreen, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(nombre, color = colors.textPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(rol, color = SenaGreen, fontSize = 12.sp)
            Text("$doc • $email", color = colors.textSecondary, fontSize = 11.sp)
        }
    }
}

@Composable
fun customTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = SenaGreen, unfocusedBorderColor = LocalAppColors.current.textSecondary.copy(alpha = 0.5f),
    focusedTextColor = LocalAppColors.current.textPrimary, unfocusedTextColor = LocalAppColors.current.textPrimary
)

@Composable
fun StatCard(label: String, value: String, icon: ImageVector, modifier: Modifier) {
    val colors = LocalAppColors.current
    Box(
        modifier = modifier.shadow(10.dp, RoundedCornerShape(12.dp)).background(colors.cardBackground, RoundedCornerShape(12.dp)).border(1.dp, colors.border, RoundedCornerShape(12.dp)).padding(20.dp)
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
        modifier = Modifier.fillMaxWidth().shadow(20.dp, RoundedCornerShape(16.dp)).background(colors.cardBackground, RoundedCornerShape(16.dp)).border(1.dp, SenaGreen.copy(0.2f), RoundedCornerShape(16.dp)).padding(20.dp)
    ) {
        Text(title, color = colors.textPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(subtitle, color = colors.textSecondary, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            Column(modifier = Modifier.widthIn(min = 500.dp)) { content() }
        }
    }
}

@Composable
private fun filaPerfil(label: String, valor: String) {
    val colors = LocalAppColors.current
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Text("$label: ", color = SenaGreen, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(valor, color = colors.textPrimary, fontSize = 14.sp)
    }
}

@Composable
fun PerfilInstructorView(onBack: () -> Unit) {
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
                Text("Carlos Mendez", color = colors.textPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Instructor", color = SenaGreen, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(24.dp))
                filaPerfil("Correo", "carlos.mendez@sena.edu.co")
                filaPerfil("Documento", "12.345.678")
                filaPerfil("Area", "Sistemas")
            }
        }
    }
}
