package ui.view

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.AutoExtendedFAB(
    iconPath: String = "add_white_24dp.svg",
    text: String = "添加",
    onClick: () -> Unit,
    state: LazyListState
)
= FloatingActionButton(
    modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd),
    onClick = onClick,
    content = {
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Icon(painterResource(iconPath), null)
            androidx.compose.animation.AnimatedVisibility(visible = state.isScrollingUp()) {
                Text(
                    modifier = Modifier.padding(start = 8.dp, top = 3.dp),
                    text = text
                )
            }
        }
    }
)