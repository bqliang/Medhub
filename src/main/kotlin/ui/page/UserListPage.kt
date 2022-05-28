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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import logic.searchUsers
import model.UserSearchCondition
import ui.card.UserCard
import ui.view.AutoExtendedFAB
import ui.view.ScrollToTopBtn
import viewmodel.HandleUserPageViewModel
import viewmodel.UserListPageViewModel

private val viewModel = UserListPageViewModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun UserListPage() = Column {
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
            cells = GridCells.Adaptive(minSize = 300.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            state = state
        ){
            items(viewModel.users) { user ->
                UserCard(user)
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(state)
        )

        ScrollToTopBtn(showScrollToTopBtn, state, scope)

        AutoExtendedFAB(
            onClick = { HandleUserPageViewModel.startHandleUserPage() },
            state = state
        )
    }
}


// 用户搜索栏
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchBar() = Row(
    modifier = Modifier
        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
        .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
) {
    OutlinedTextField(
        modifier = Modifier
            .weight(1f)
            .onPreviewKeyEvent { keyEvent ->
                if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Enter) {
                    searchUsers()
                    true
                } else if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.CtrlLeft) {
                    val conditions = UserSearchCondition.values()
                    var index = conditions.indexOf(viewModel.userSearchCondition)
                    index = if (index == conditions.lastIndex) 0 else index + 1
                    viewModel.userSearchCondition = conditions[index]
                    true
                } else false
            },
        label = { Text("按${viewModel.userSearchCondition.str}搜索") },
        singleLine = true,
        leadingIcon = { Icon(painterResource("search_white_24dp.svg"), null) },
        trailingIcon = { SearchConditionsMenu() },
        value = viewModel.searchKeyword,
        onValueChange = { viewModel.searchKeyword = it }
    )
}


// 搜索条件筛选
@Composable
private fun SearchConditionsMenu() = Box(modifier = Modifier.wrapContentSize()) {
    DropdownMenu(
        expanded = viewModel.conditionMenuExpanded,
        onDismissRequest = { viewModel.conditionMenuExpanded = false },
    ) {
        UserSearchCondition.values().forEach { condition ->
            DropdownMenuItem(
                onClick = {
                    viewModel.userSearchCondition = condition
                    viewModel.conditionMenuExpanded = false
                },
                content = { Text(condition.str) }
            )
        }
    }
    IconButton(
        onClick = { viewModel.conditionMenuExpanded = true },
        content = {
            Icon(
                painter = painterResource(if (viewModel.conditionMenuExpanded) "expand_less_white_24dp.svg" else "expand_more_white_24dp.svg"),
                contentDescription = null
            )
        }
    )
}




