package com.example.sennaccess.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val SenaGreen = Color(0xFF02D914)
val WarningYellow = Color(0xFFFFC107)
val ErrorRed = Color(0xFFFF6B6B)
val OrangeAmber = Color(0xFFFFA726)

data class AppColors(
    val background: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textOnPrimary: Color,
    val border: Color,
    val borderLight: Color,
    val cardBackground: Color,
    val inputBackground: Color,
    val successBackground: Color,
    val warningBackground: Color,
    val errorBackground: Color,
    val bottomNavBar: Color,
    val topBarBackground: Color,
    val iconTint: Color,
    val divider: Color,
    val inputText: Color,
    val inputLabel: Color,
    val navigationIndicator: Color,
    val headerText: Color,
    val subtitleText: Color,
    val statCardBackground: Color,
    val tableRowEven: Color,
    val tableRowOdd: Color,
    val tableHeaderBackground: Color,
    val scrollbarThumb: Color,
    val overlayBackground: Color,
    val chipBackground: Color,
    val chipText: Color,
    val buttonSecondaryBackground: Color,
    val buttonSecondaryText: Color,
)

fun darkAppColors() = AppColors(
    background = Color(0xFF07090D),
    surface = Color(0xFF13161C),
    surfaceVariant = Color(0xFF1A2128),
    textPrimary = Color.White,
    textSecondary = Color.White.copy(alpha = 0.5f),
    textOnPrimary = Color.White,
    border = Color.White.copy(alpha = 0.05f),
    borderLight = Color.White.copy(alpha = 0.2f),
    cardBackground = Color(0xFF13161C).copy(alpha = 0.8f),
    inputBackground = Color(0xFF13161C).copy(alpha = 0.85f),
    successBackground = SenaGreen.copy(alpha = 0.08f),
    warningBackground = WarningYellow.copy(alpha = 0.08f),
    errorBackground = ErrorRed.copy(alpha = 0.08f),
    bottomNavBar = Color.Black.copy(alpha = 0.95f),
    topBarBackground = Color.Black.copy(alpha = 0.4f),
    iconTint = Color.White,
    divider = Color.White.copy(alpha = 0.05f),
    inputText = Color.White,
    inputLabel = Color.White.copy(alpha = 0.5f),
    navigationIndicator = SenaGreen.copy(alpha = 0.15f),
    headerText = Color.White,
    subtitleText = Color.White.copy(alpha = 0.5f),
    statCardBackground = Color(0xFF13161C).copy(alpha = 0.8f),
    tableRowEven = Color(0xFF13161C).copy(alpha = 0.5f),
    tableRowOdd = Color(0xFF1A2128).copy(alpha = 0.5f),
    tableHeaderBackground = Color(0xFF0E1417),
    scrollbarThumb = Color.Gray,
    overlayBackground = Color.Black.copy(alpha = 0.5f),
    chipBackground = Color(0xFF13161C).copy(alpha = 0.8f),
    chipText = Color.White,
    buttonSecondaryBackground = Color(0xFF13161C).copy(alpha = 0.8f),
    buttonSecondaryText = Color.White,
)

fun lightAppColors() = AppColors(
    background = Color(0xFFF5F5F5),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFEEEEEE),
    textPrimary = Color(0xFF1C1B1F),
    textSecondary = Color(0xFF6B7280),
    textOnPrimary = Color.White,
    border = Color.Black.copy(alpha = 0.08f),
    borderLight = SenaGreen.copy(alpha = 0.4f),
    cardBackground = Color.White.copy(alpha = 0.9f),
    inputBackground = Color.White.copy(alpha = 0.9f),
    successBackground = SenaGreen.copy(alpha = 0.08f),
    warningBackground = WarningYellow.copy(alpha = 0.08f),
    errorBackground = ErrorRed.copy(alpha = 0.08f),
    bottomNavBar = Color.White.copy(alpha = 0.95f),
    topBarBackground = Color.White.copy(alpha = 0.9f),
    iconTint = Color(0xFF1C1B1F),
    divider = Color.Black.copy(alpha = 0.08f),
    inputText = Color(0xFF1C1B1F),
    inputLabel = Color(0xFF6B7280),
    navigationIndicator = SenaGreen.copy(alpha = 0.15f),
    headerText = Color(0xFF1C1B1F),
    subtitleText = Color(0xFF6B7280),
    statCardBackground = Color.White.copy(alpha = 0.9f),
    tableRowEven = Color.White.copy(alpha = 0.5f),
    tableRowOdd = Color(0xFFF0F0F0).copy(alpha = 0.5f),
    tableHeaderBackground = Color(0xFFE8E8E8),
    scrollbarThumb = Color.Gray,
    overlayBackground = Color.Black.copy(alpha = 0.3f),
    chipBackground = Color.White.copy(alpha = 0.9f),
    chipText = Color(0xFF1C1B1F),
    buttonSecondaryBackground = Color.White.copy(alpha = 0.9f),
    buttonSecondaryText = Color(0xFF1C1B1F),
)
