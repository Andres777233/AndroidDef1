package com.example.sennaccess.ui.theme



import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.sennaccess.ui.theme.LocalAppColors
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode

@Composable
fun RegisterScreen(onBackToLogin: () -> Unit, isDark: Boolean = true, onToggleTheme: () -> Unit = {}) {
    val colors = LocalAppColors.current
    // Variables locales solo para que la vista interactúe visualmente (no guarda nada)
    var identification by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }
    var courseNumber by remember { mutableStateOf("") }
    var program by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        // Patrón de fondo traslúcido
        Image(
            painter = rememberAsyncImagePainter("https://www.sena.edu.co/Style%20Library/alayout/images/pattern.png"),
            contentDescription = null,
            modifier = Modifier.fillMaxSize().graphicsLayer(alpha = 0.12f),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Tarjeta de cristal con neón
            GlassCardRegister {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()), // Scroll habilitado
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Toggle tema dentro del card
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = onToggleTheme) {
                            Icon(
                                imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = if (isDark) "Modo claro" else "Modo oscuro",
                                tint = colors.textPrimary
                            )
                        }
                    }

                    // Cabecera
                    Image(
                        painter = rememberAsyncImagePainter("https://www.sena.edu.co/Style%20Library/alayout/images/logoSena.png?rev=40"),
                        contentDescription = "Logo SENA",
                        modifier = Modifier.size(90.dp).padding(bottom = 12.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            append("Sena ")
                            withStyle(style = SpanStyle(color = SenaGreen, fontWeight = FontWeight.Bold)) { append("Access") }
                        },
                        fontSize = 28.sp, fontWeight = FontWeight.Bold, color = colors.textPrimary
                    )
                    Text("Crea tu cuenta institucional", fontSize = 14.sp, color = SenaGreen.copy(alpha = 0.8f))
                    Spacer(modifier = Modifier.height(32.dp))

                    // --- SECCIÓN 1: INFORMACIÓN PERSONAL ---
                    SectionHeader(icon = Icons.Default.Person, title = "Información Personal")
                    NeonTextField(value = identification, onValueChange = { identification = it }, label = "Número de Identificación")
                    Spacer(modifier = Modifier.height(12.dp))
                    NeonTextField(value = name, onValueChange = { name = it }, label = "Nombres")
                    Spacer(modifier = Modifier.height(12.dp))
                    NeonTextField(value = lastname, onValueChange = { lastname = it }, label = "Apellidos")

                    Spacer(modifier = Modifier.height(24.dp))

                    // --- SECCIÓN 2: FORMACIÓN ACADÉMICA ---
                    SectionHeader(icon = Icons.Default.School, title = "Formación Académica")
                    NeonTextField(value = email, onValueChange = { email = it }, label = "Correo Electrónico Institucional")
                    Spacer(modifier = Modifier.height(12.dp))
                    NeonTextField(value = courseNumber, onValueChange = { courseNumber = it }, label = "Número de Ficha")
                    Spacer(modifier = Modifier.height(12.dp))
                    NeonTextField(value = program, onValueChange = { program = it }, label = "Programa de Formación")

                    Spacer(modifier = Modifier.height(24.dp))

                    // --- SECCIÓN 3: SEGURIDAD ---
                    SectionHeader(icon = Icons.Default.Lock, title = "Seguridad de la Cuenta")
                    NeonTextField(
                        value = password, onValueChange = { password = it }, label = "Crear Contraseña",
                        isPassword = true, showPassword = showPassword, onTogglePassword = { showPassword = !showPassword }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    NeonTextField(
                        value = confirmPassword, onValueChange = { confirmPassword = it }, label = "Confirmar Contraseña",
                        isPassword = true, showPassword = showPassword, onTogglePassword = { showPassword = !showPassword }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // BOTONES
                    PrimaryNeonButtonRegister(text = "CREAR CUENTA", icon = Icons.Default.HowToReg, onClick = { /* Solo vista */ }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(16.dp))
                    GlowOutlinedButtonRegister(text = "VOLVER AL LOGIN", icon = Icons.AutoMirrored.Filled.Login, onClick = onBackToLogin, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

// --- COMPONENTES VISUALES INTERNOS ---

@Composable
fun SectionHeader(icon: ImageVector, title: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = SenaGreen.copy(alpha = 0.8f), modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = title.uppercase(), color = SenaGreen.copy(alpha = 0.8f), fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
    }
}

@Composable
fun NeonTextField(
    value: String, onValueChange: (String) -> Unit, label: String,
    isPassword: Boolean = false, showPassword: Boolean = false, onTogglePassword: () -> Unit = {}
) {
    val colors = LocalAppColors.current
    OutlinedTextField(
        value = value, onValueChange = onValueChange,
        label = { Text(label, color = colors.textSecondary, fontSize = 14.sp) },
        modifier = Modifier.fillMaxWidth(), singleLine = true,
        visualTransformation = if (isPassword && !showPassword) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = onTogglePassword) {
                    Icon(
                        imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null, tint = colors.textSecondary
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SenaGreen, unfocusedBorderColor = colors.divider,
            focusedTextColor = colors.textPrimary, unfocusedTextColor = colors.textPrimary,
            focusedContainerColor = colors.surfaceVariant.copy(alpha = 0.5f), unfocusedContainerColor = colors.surfaceVariant.copy(alpha = 0.5f)
        )
    )
}

@Composable
fun GlassCardRegister(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    val colors = LocalAppColors.current
    Box(
        modifier = modifier.fillMaxWidth(0.95f).padding(vertical = 20.dp)
            .shadow(15.dp, RoundedCornerShape(20.dp), spotColor = SenaGreen, ambientColor = SenaGreen.copy(alpha = 0.3f))
            .clip(RoundedCornerShape(20.dp))
            .border(2.dp, SenaGreen, RoundedCornerShape(20.dp))
            .background(colors.cardBackground),
        content = content
    )
}

@Composable
fun PrimaryNeonButtonRegister(text: String, icon: ImageVector? = null, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.shadow(15.dp, RoundedCornerShape(12.dp), spotColor = SenaGreen),
        colors = ButtonDefaults.buttonColors(containerColor = SenaGreen, contentColor = Color.Black),
        shape = RoundedCornerShape(12.dp), contentPadding = PaddingValues(vertical = 14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(text = text, fontWeight = FontWeight.ExtraBold, letterSpacing = 2.sp)
        }
    }
}

@Composable
fun GlowOutlinedButtonRegister(text: String, icon: ImageVector? = null, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val colors = LocalAppColors.current
    OutlinedButton(
        onClick = onClick, modifier = modifier,
        border = BorderStroke(2.dp, SenaGreen.copy(alpha = 0.5f)),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = colors.textPrimary),
        shape = RoundedCornerShape(12.dp), contentPadding = PaddingValues(vertical = 14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(20.dp), tint = SenaGreen)
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(text = text, letterSpacing = 2.sp)
        }
    }
}