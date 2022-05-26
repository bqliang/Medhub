import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ui.page.Dashboard
import ui.page.LoginPage
import ui.theme.MedhubTheme
import ui.view.SlideVisibility

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(
            size = DpSize(900.dp, 650.dp),
            position = WindowPosition(Alignment.Center)
        ),
        icon = painterResource("icon_128px.png"),
        title = "MedHub",
        content = { App() }
    )
}


@Composable
fun App() {
    MedhubTheme(isLight = !isDark) {
        Surface(modifier = Modifier.fillMaxSize()) {
            SlideVisibility(loginSuccessful) { Dashboard() }
            SlideVisibility(!loginSuccessful) { LoginPage() }
        }
    }
}