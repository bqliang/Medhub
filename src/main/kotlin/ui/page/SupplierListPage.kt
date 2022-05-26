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
import logic.exportMedicines
import logic.exportSuppliers
import logic.searchMedicine
import model.MedicinePageState
import model.MedicineSearchCondition
import model.SupplierPageState
import model.SupplierSearchCondition
import supplierPageState
import ui.card.MedicineCard
import ui.card.SupplierCard
import ui.view.AutoExtendedFAB
import ui.view.ScrollToTopBtn
import viewmodel.HandleMedicinePageViewModel
import viewmodel.HandleSupplierPageViewModel
import viewmodel.MedicineListPageViewModel
import viewmodel.SupplierListPageViewModel

private val viewModel = SupplierListPageViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SupplierListPage() = Column {
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
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            state = state
        ) {
            items(viewModel.suppliers) { supplier ->
                SupplierCard(supplier)
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(state)
        )

        ScrollToTopBtn(showScrollToTopBtn, state, scope)

        AutoExtendedFAB(
            onClick = {
                HandleSupplierPageViewModel.setData()
                supplierPageState = SupplierPageState.Add
            },
            state = state
        )
    }
}


// 供应商搜索栏
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchBar() = Row(
    modifier = Modifier
        .wrapContentHeight()
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp, top = 8.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    OutlinedTextField(
        modifier = Modifier
            .weight(1f)
            .onPreviewKeyEvent { keyEvent ->
                if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Enter) {
                    searchMedicine()
                    true
                } else if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.CtrlLeft) {
                    val conditions = SupplierSearchCondition.values()
                    var index = conditions.indexOf(viewModel.supplierSearchCondition)
                    index = if (index == conditions.lastIndex) 0 else index + 1
                    viewModel.supplierSearchCondition = conditions[index]
                    true
                } else false
            },
        label = { Text("按${viewModel.supplierSearchCondition.str}搜索") },
        singleLine = true,
        leadingIcon = { Icon(painterResource("search_white_24dp.svg"), null) },
        trailingIcon = { SearchConditionsMenu() },
        value = viewModel.searchKeyword,
        onValueChange = { viewModel.searchKeyword = it }
    )

    Spacer(modifier = Modifier.width(16.dp))

    OutlinedButton(
        modifier = Modifier.padding(top = 8.dp),
        onClick = { exportSuppliers() },
        content = {
            Icon(painterResource("file_export_white_24dp.svg"), null)
            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
            Text("导出")
        }
    )
}

// 搜索条件筛选
@Composable
private fun SearchConditionsMenu() = Box(modifier = Modifier.wrapContentSize()) {
    DropdownMenu(
        expanded = viewModel.conditionMenuExpanded,
        onDismissRequest = { viewModel.conditionMenuExpanded = false },
    ) {
        SupplierSearchCondition.values().forEach { condition ->
            DropdownMenuItem(
                onClick = {
                    viewModel.supplierSearchCondition = condition
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