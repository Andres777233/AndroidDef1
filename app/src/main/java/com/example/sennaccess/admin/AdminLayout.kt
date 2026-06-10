package com.example.sennaccess.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.sennaccess.ui.theme.LocalAppColors
import com.example.sennaccess.ui.theme.SenaGreen

@Composable
fun AdminScreenLayout(
    onLogout: () -> Unit,
    onBack: (() -> Unit)? = null,
    onNavigate: ((AdminScreen) -> Unit)? = null,
    isDark: Boolean = true,
    onToggleTheme: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    val colors = LocalAppColors.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .systemBarsPadding()
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://www.sena.edu.co/Style%20Library/alayout/images/pattern.png"),
            contentDescription = null,
            modifier = Modifier.fillMaxSize().graphicsLayer(alpha = 0.15f),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.fillMaxSize()) {
            AdminTopBar(onLogout = onLogout, onNavigate = onNavigate, isDark = isDark, onToggleTheme = onToggleTheme)
            if (onBack != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colors.overlayBackground)
                        .padding(start = 8.dp)
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null, tint = colors.iconTint)
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                content = content
            )
        }
    }
}

@Composable
fun AdminTopBar(
    onLogout: () -> Unit,
    onNavigate: ((AdminScreen) -> Unit)? = null,
    isDark: Boolean = true,
    onToggleTheme: () -> Unit = {}
) {
    val colors = LocalAppColors.current
    var showMenu by remember { mutableStateOf(false) }

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
                    Text("ADMINISTRADOR", color = SenaGreen, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onToggleTheme) {
                    Icon(
                        if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                        null,
                        tint = colors.iconTint
                    )
                }
                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.Menu, null, tint = colors.iconTint)
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
                                    Text("Administrador", color = colors.textPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                    Text("admin@sena.edu.co", color = colors.textSecondary, fontSize = 12.sp)
                                }
                            }
                        }
                        HorizontalDivider(color = colors.border)
                        if (onNavigate != null) {
                            DropdownMenuItem(
                                text = { Text("Perfil", color = colors.textPrimary) },
                                leadingIcon = { Icon(Icons.Default.Person, null, tint = SenaGreen) },
                                onClick = { showMenu = false; onNavigate(AdminScreen.PERFIL) }
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
fun AdminGlassCard(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    val colors = LocalAppColors.current
    Box(
        modifier = modifier
            .shadow(10.dp, RoundedCornerShape(12.dp))
            .background(colors.cardBackground, RoundedCornerShape(12.dp))
            .border(1.dp, colors.border, RoundedCornerShape(12.dp))
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, content = content)
    }
}

@Composable
fun AdminGlassContainer(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    val colors = LocalAppColors.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(10.dp, RoundedCornerShape(12.dp))
            .background(colors.cardBackground, RoundedCornerShape(12.dp))
            .border(1.dp, colors.border, RoundedCornerShape(12.dp))
            .padding(16.dp),
        content = content
    )
}
