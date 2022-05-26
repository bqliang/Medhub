package ui.view

import androidx.compose.animation.*
import androidx.compose.runtime.Composable

/**
 * 切片效果
 *
 * @param visible 显示条件
 * @param content 内容
 */
@Composable
fun SlideVisibility(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) = AnimatedVisibility(
    visible = visible,
    enter = slideInHorizontally() + fadeIn(),
    exit = slideOutHorizontally() + fadeOut(),
    content = content
)
