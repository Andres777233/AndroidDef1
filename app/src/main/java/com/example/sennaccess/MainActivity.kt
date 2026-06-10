package com.example.sennaccess

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sennaccess.ui.theme.*
import com.example.sennaccess.admin.InstructorDashboard
import com.example.sennaccess.admin.*
import com.example.sennaccess.aprendiz.AprendizDashboard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDark by rememberSaveable { mutableStateOf(true) }
            val appColors = if (isDark) darkAppColors() else lightAppColors()

            CompositionLocalProvider(LocalAppColors provides appColors) {
                SennaccessTheme(darkTheme = isDark) {

                    val window = (LocalContext.current as? ComponentActivity)?.window
                    DisposableEffect(isDark) {
                        WindowCompat.getInsetsController(window!!, window.decorView)
                            .isAppearanceLightStatusBars = !isDark
                        window.navigationBarColor = android.graphics.Color.TRANSPARENT
                        onDispose { }
                    }

                    var currentScreen by rememberSaveable { mutableStateOf("splash") }
                    var adminScreen by rememberSaveable  { mutableStateOf(AdminScreen.PANEL) }
                    var usuarioId by rememberSaveable  { mutableIntStateOf(0) }
                    var usuarioNombre by rememberSaveable  { mutableStateOf("") }
                    var tituloMensaje by rememberSaveable  { mutableStateOf("") }
                    var descripcionMensaje by rememberSaveable  { mutableStateOf("") }
                    var tipoMensaje by rememberSaveable  { mutableStateOf("success") }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(if (isDark) Color(0xFF07090D) else Color(0xFFF5F5F5))
                    ) {
                        when (currentScreen) {
                            "splash" -> SplashScreen(
                                onFinished = { currentScreen = "landing" },
                                isDark = isDark
                            )
                            "landing" -> LandingScreen(
                                onNavigateToLogin = { currentScreen = "login" },
                                onNavigateToRegister = { currentScreen = "register" },
                                isDark = isDark,
                                onToggleTheme = { isDark = !isDark }
                            )
                            "login" -> LoginScreen(
                                onNavigateToRegister = { currentScreen = "register" },
                                onNavigateToRecovery = { currentScreen = "recovery" },
                                onNavigateToFingerprint = { currentScreen = "fingerprint" },
                                onNavigateToAprendiz = { currentScreen = "aprendiz_dashboard" },
                                onNavigateToInstructor = { currentScreen = "instructor_dashboard" },
                                onLoginSuccess = {
                                    adminScreen = AdminScreen.PANEL
                                    currentScreen = "admin"
                                },
                                isDark = isDark,
                                onToggleTheme = { isDark = !isDark }
                            )
                            "register" -> RegisterScreen(
                                onBackToLogin = { currentScreen = "login" },
                                isDark = isDark,
                                onToggleTheme = { isDark = !isDark }
                            )
                            "recovery" -> PasswordRecoveryScreen(
                                onBackToLogin = { currentScreen = "landing" },
                                isDark = isDark,
                                onToggleTheme = { isDark = !isDark }
                            )
                            "fingerprint" -> FingerprintScreen(
                                onBackToLogin = { currentScreen = "login" },
                                onNavigateToLoading = { currentScreen = "landing" },
                                isDark = isDark,
                                onToggleTheme = { isDark = !isDark }
                            )
                            "aprendiz_dashboard" -> AprendizDashboard(
                                onCerrarSesion = { currentScreen = "landing" },
                                isDark = isDark,
                                onToggleTheme = { isDark = !isDark }
                            )
                            "instructor_dashboard" -> InstructorDashboard(
                                onCerrarSesion = { currentScreen = "landing" },
                                isDark = isDark,
                                onToggleTheme = { isDark = !isDark }
                            )
                            "admin" -> {
                                when (adminScreen) {
                                    AdminScreen.PANEL -> PanelPrincipal(
                                        onNavigate = { screen -> adminScreen = screen },
                                        onCerrarSesion = { currentScreen = "landing" },
                                        isDark = isDark,
                                        onToggleTheme = { isDark = !isDark }
                                    )
                                    AdminScreen.USUARIOS -> Usuarios(
                                        onNavigate = { screen -> adminScreen = screen },
                                        onCerrarSesion = { currentScreen = "landing" },
                                        onEditarUsuario = { id, nombre ->
                                            usuarioId = id
                                            usuarioNombre = nombre
                                            adminScreen = AdminScreen.ACTUALIZAR_USUARIO
                                        },
                                        isDark = isDark,
                                        onToggleTheme = { isDark = !isDark }
                                    )
                                    AdminScreen.CREAR_USUARIO -> CrearUsuario(
                                        onNavigate = { screen -> adminScreen = screen },
                                        onCerrarSesion = { currentScreen = "landing" },
                                        isDark = isDark,
                                        onToggleTheme = { isDark = !isDark }
                                    )
                                    AdminScreen.ACTUALIZAR_USUARIO -> ActualizarUsuario(
                                        usuarioId = usuarioId,
                                        usuarioNombre = usuarioNombre,
                                        onNavigate = { screen -> adminScreen = screen },
                                        onCerrarSesion = { currentScreen = "landing" },
                                        isDark = isDark,
                                        onToggleTheme = { isDark = !isDark }
                                    )
                                    AdminScreen.ACCESO_APRENDICES -> AccesoAprendices(
                                        onNavigate = { screen -> adminScreen = screen },
                                        onCerrarSesion = { currentScreen = "landing" },
                                        isDark = isDark,
                                        onToggleTheme = { isDark = !isDark }
                                    )
                                    AdminScreen.ACCESO_INSTRUCTORES -> AccesoInstructores(
                                        onNavigate = { screen -> adminScreen = screen },
                                        onCerrarSesion = { currentScreen = "landing" },
                                        isDark = isDark,
                                        onToggleTheme = { isDark = !isDark }
                                    )
                                    AdminScreen.REPORTE_NOVEDADES -> ReporteNovedades(
                                        onNavigate = { screen -> adminScreen = screen },
                                        onCerrarSesion = { currentScreen = "landing" },
                                        isDark = isDark,
                                        onToggleTheme = { isDark = !isDark }
                                    )
                                    AdminScreen.REPORTE_INSTRUCTOR -> ReporteInstructor(
                                        onNavigate = { screen -> adminScreen = screen },
                                        onCerrarSesion = { currentScreen = "landing" },
                                        isDark = isDark,
                                        onToggleTheme = { isDark = !isDark }
                                    )
                                    AdminScreen.PERFIL -> Perfil(
                                        onNavigate = { screen -> adminScreen = screen },
                                        onCerrarSesion = { currentScreen = "landing" },
                                        isDark = isDark,
                                        onToggleTheme = { isDark = !isDark }
                                    )
                                    AdminScreen.MENSAJE -> Mensaje(
                                        titulo = tituloMensaje,
                                        descripcion = descripcionMensaje,
                                        tipo = tipoMensaje,
                                        onVolver = { adminScreen = AdminScreen.PANEL },
                                        isDark = isDark,
                                        onToggleTheme = { isDark = !isDark }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
