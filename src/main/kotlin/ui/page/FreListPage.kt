package ui.page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import logic.searchFre
import model.FreType
import ui.card.FreCard
import ui.view.AutoExtendedFAB
import ui.view.ScrollToTopBtn
import viewmodel.FreListViewModel

private val viewModel = FreListViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FreListPage() = Column {
    SearchBar()
    Box(modifier = Modifier.fillMaxSize()) {

        val state = rememberLazyListState()
        val scope = rememberCoroutineScope()

        val showScrollToTopBtn by remember {
            derivedStateOf {         // 尽量减少不必要的合成
                state.firstVisibleItemIndex > 0
            }
        }

        LazyVerticalGrid(
            modifier = Modifier.padding(end = 8.dp).fillMaxSize(),
            cells = GridCells.Adaptive(minSize = 350.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            state = state
        ){
            items(viewModel.fres) { fre ->
                FreCard(fre)
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(state)
        )
        
        ScrollToTopBtn(showScrollToTopBtn, state, scope)

        AutoExtendedFAB(
            onClick = {

            },
            state = state
        )
    }

    LaunchedEffect(viewModel.fres) {
        searchFre()
    }
}


// 财政收支搜索栏
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchBar() = Row(
    modifier = Modifier
        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
        .wrapContentHeight()
        .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
) {
    OutlinedTextField(
        modifier = Modifier
            .weight(1f)
            .onPreviewKeyEvent { keyEvent ->
                if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Enter) {
                    searchFre()
                    true
                }
                else false
            },
        label = { Text("按财政收支编号搜索") },
        singleLine = true,
        leadingIcon = { Icon(painterResource("search_white_24dp.svg"), null) },
        value = viewModel.searchKeyword,
        onValueChange = { viewModel.searchKeyword = it }
    )

    Spacer(modifier = Modifier.width(16.dp))

    Box {
        DropdownMenu(
            expanded = viewModel.showTypeMenu,
            onDismissRequest = { viewModel.showTypeMenu = false },
        ) {
            FreType.values().forEach { freType ->
                DropdownMenuItem(
                    onClick = {
                        viewModel.fresType = freType
                        viewModel.showTypeMenu = false
                        searchFre()
                    },
                    content = { Text(freType.str) }
                )
            }
        }
        OutlinedButton(
            modifier = Modifier.padding(top = 8.dp),
            onClick = { viewModel.showTypeMenu = true },
            content = {
                Icon(painterResource("filter_alt_white_24dp.svg"), null)
                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                Text(
                    text = "筛选：${viewModel.fresType.str}",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        )
    }
}