package ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BoxScope.ScrollToTopBtn(visible: Boolean, state: LazyListState, scope: CoroutineScope) =
    AnimatedVisibility(
        visible = visible,
        modifier = Modifier.align(Alignment.TopCenter).padding(16.dp),
        content = {
            FloatingActionButton(
                modifier = Modifier.size(40.dp),
                onClick = {
                    scope.launch { state.animateScrollToItem(0) }
                },
                content = { Icon(painterResource("arrow_upward_white_24dp.svg"), null) }
            )
        },
        enter = slideInVertically(),
        exit = slideOutVertically()
    )