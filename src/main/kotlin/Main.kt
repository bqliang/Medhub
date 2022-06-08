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
import model.CheckoutPageState
import ui.page.CheckoutPage
import ui.page.Dashboard
import ui.page.LoginPage
import ui.theme.MedhubTheme
import ui.view.SlideVisibility
import viewmodel.CheckoutPageViewModel

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(
            size = DpSize(900.dp, 680.dp),
            position = WindowPosition(Alignment.Center)
        ),
        icon = painterResource("icon_128px.png"),
        title = "MedHub",
        content = { App() }
    )

    if (checkoutWindowState != CheckoutPageState.Invisible) {
        Window(
            onCloseRequest = { CheckoutPageViewModel.closeCheckoutWindow()},
            state = rememberWindowState(size = DpSize(900.dp, 680.dp),),
            icon = painterResource("icon_128px.png"),
            title = if (checkoutWindowState == CheckoutPageState.Sales) "销售结算单" else "采购结算单",
            content = {
                MedhubTheme(isLight = !isDark){
                    Surface(modifier = Modifier.fillMaxSize()) {
                        CheckoutPage()
                    }
                }
            }
        )
    }
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