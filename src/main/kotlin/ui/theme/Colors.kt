package ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color


@Composable
fun MedhubTheme(isLight: Boolean, content: @Composable () -> Unit) {
    val primary by animateColorAsState(if (isLight) Color(0xFF6200EE) else Color(0xFFBB86FC), TweenSpec(600))
    val primaryVariant by animateColorAsState(if (isLight) Color(0xFF3700B3) else Color(0xFF3700B3), TweenSpec(600))
    val secondary by animateColorAsState(if (isLight) Color(0xFF03DAC6)  else Color(0xFF03DAC6), TweenSpec(600))
    val secondaryVariant by animateColorAsState(if (isLight) Color(0xFF018786) else Color(0xFF03DAC6), TweenSpec(600))
    val background by animateColorAsState(if (isLight) Color.White else Color(0xFF121212), TweenSpec(600))
    val surface by animateColorAsState(if (isLight) Color.White else Color(0xFF121212), TweenSpec(600))
    val error by animateColorAsState(if (isLight) Color(0xFFB00020) else Color(0xFFCF6679), TweenSpec(600))
    val onPrimary by animateColorAsState(if (isLight) Color.White else Color.Black, TweenSpec(600))
    val onSecondary by animateColorAsState(if (isLight) Color.Black else Color.Black, TweenSpec(600))
    val onBackground by animateColorAsState(if (isLight) Color.Black else Color.White, TweenSpec(600))
    val onSurface by animateColorAsState(if (isLight) Color.Black else Color.White, TweenSpec(600))
    val onError by animateColorAsState(if (isLight) Color.White else Color.Black, TweenSpec(600))

    MaterialTheme(
        colors = Colors(
            primary,
            primaryVariant,
            secondary,
            secondaryVariant,
            background,
            surface,
            error,
            onPrimary,
            onSecondary,
            onBackground,
            onSurface,
            onError,
            isLight = isLight
        ),
        shapes = myShapes,
        content = content
    )
}