package com.example.sennaccess.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sennaccess.ui.theme.LocalAppColors
import com.example.sennaccess.ui.theme.OrangeAmber
import com.example.sennaccess.ui.theme.SenaGreen

@Composable
fun Mensaje(
    titulo: String,
    descripcion: String,
    tipo: String,
    onVolver: () -> Unit,
    isDark: Boolean = true,
    onToggleTheme: () -> Unit = {}
) {
    val colors = LocalAppColors.current
    val esAdvertencia = tipo == "advertencia"

    AdminScreenLayout(
        onLogout = { },
        onBack = onVolver,
        isDark = isDark,
        onToggleTheme = onToggleTheme
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .shadow(10.dp, RoundedCornerShape(12.dp))
                    .background(colors.cardBackground, RoundedCornerShape(12.dp))
                    .border(1.dp, if (esAdvertencia) OrangeAmber else colors.border, RoundedCornerShape(12.dp))
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = if (esAdvertencia) Icons.Default.Warning else Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = if (esAdvertencia) OrangeAmber else SenaGreen,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(titulo, color = colors.textPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(12.dp))
                Text(descripcion, color = colors.textSecondary, fontSize = 15.sp, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = onVolver,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SenaGreen, contentColor = colors.textPrimary)
                ) { Text("Volver al panel", fontWeight = FontWeight.Bold, fontSize = 16.sp) }
            }
        }
    }
}
