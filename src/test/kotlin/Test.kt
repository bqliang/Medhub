import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlin.test.Test


class Test {

    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    @Test
    fun test() = application {
        Window(
            onCloseRequest = ::exitApplication,
            state = rememberWindowState(
                size = DpSize(500.dp, 500.dp),
                position = WindowPosition(Alignment.Center)
            ),
            icon = painterResource("icon_24px.png"),
            title = "MedHub"
        ){
            MaterialTheme {
//                Surface(modifier = Modifier.fillMaxSize()) {
//                    Box(modifier = Modifier.fillMaxSize()){
//                        List()
//                    }
//                }
                Box(modifier = Modifier.fillMaxSize()){
                    List()
                }

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun BoxScope.List() = LazyVerticalGrid(
    modifier = Modifier.fillMaxSize(),
    cells = GridCells.Fixed(2),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
){
    items(100){
        Card(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center),
            elevation = 4.dp,
            contentColor = Color.Magenta,
            onClick = {  }
        ){

        }
    }
}
