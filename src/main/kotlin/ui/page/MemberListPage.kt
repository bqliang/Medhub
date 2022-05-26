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
import androidx.compose.material.icons.Icons
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
import logic.exportMembers
import logic.searchMember
import memberPageState
import model.MemberPageState
import model.MemberSearchCondition
import ui.card.MemberCard
import ui.view.AutoExtendedFAB
import ui.view.ScrollToTopBtn
import viewmodel.HandleMemberPageViewModel
import viewmodel.MemberListPageViewModel

private val viewModel = MemberListPageViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MemberListPage() = Column {
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
            items(viewModel.members) { member ->
                MemberCard(member)
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(state)
        )

        ScrollToTopBtn(showScrollToTopBtn, state, scope)

        AutoExtendedFAB(
            onClick = {
                HandleMemberPageViewModel.setData()
                memberPageState = MemberPageState.Add
            },
            state = state
        )
    }
}


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
                    searchMember()
                    true
                } else if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.CtrlLeft) {
                    val conditions = MemberSearchCondition.values()
                    var index = conditions.indexOf(viewModel.memberSearchCondition)
                    index = if (index == conditions.lastIndex) 0 else index + 1
                    viewModel.memberSearchCondition = conditions[index]
                    true
                }
                else false
            },
        label = { Text("按${viewModel.memberSearchCondition.str}搜索") },
        singleLine = true,
        leadingIcon = { Icon(painterResource("search_white_24dp.svg"), null) },
        trailingIcon = { SearchConditionsMenu() },
        value = viewModel.searchKeyword,
        onValueChange = { viewModel.searchKeyword = it }
    )

    Spacer(modifier = Modifier.width(16.dp))

    OutlinedButton(
        modifier = Modifier.padding(top = 8.dp),
        onClick = { exportMembers() },
        content = {
            Icon(painterResource("file_export_white_24dp.svg"), null)
            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
            Text("导出")
        }
    )
}


@Composable
private fun SearchConditionsMenu() = Box(modifier = Modifier.wrapContentSize()) {
    DropdownMenu(
        expanded = viewModel.conditionMenuExpanded,
        onDismissRequest = { viewModel.conditionMenuExpanded = false },
    ) {
        MemberSearchCondition.values().forEach { condition ->
            DropdownMenuItem(
                onClick = {
                    viewModel.memberSearchCondition = condition
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