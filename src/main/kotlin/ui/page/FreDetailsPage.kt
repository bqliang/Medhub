package ui.page

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import db
import frePageState
import logic.addFre
import logic.deleteMedicine
import model.CheckoutPageState
import model.FrePageState
import model.MedicinePageState
import model.database.freItems
import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.toList
import ui.card.CheckoutCard
import ui.card.FreItemCard
import ui.view.ScrollToTopBtn
import utils.string
import viewmodel.CheckoutPageViewModel
import viewmodel.DashboardViewModel
import viewmodel.FreDetailsPageViewmodel
import viewmodel.FreDetailsPageViewmodel.fre

private val viewModel = FreDetailsPageViewmodel

@Composable
fun FreDetailsPage() = Column(modifier = Modifier.fillMaxSize()) {

    MyTopBar()

    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val showScrollToTopBtn by remember {
        derivedStateOf {         // 尽量减少不必要的合成
            state.firstVisibleItemIndex > 0
        }
    }

    Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
        LazyColumn(
            modifier = Modifier.padding(end = 8.dp).fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            state = state
        ) {
            items(viewModel.freItems) {
                FreItemCard(it)
            }
        }

        ScrollToTopBtn(showScrollToTopBtn, state, scope)

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(state)
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "总金额：%.2f".format(viewModel.fre.total),
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = if (viewModel.fre.type == "销售")
                "客户：${viewModel.fre.member?.name}（${viewModel.fre.member?.id}）"
            else "供应商：${viewModel.fre.supplier?.name}（${viewModel.fre.supplier?.id}）"
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "经手人：${viewModel.fre.user.name}（${viewModel.fre.user.id}）")
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = viewModel.fre.time.string)
    }

    LaunchedEffect(viewModel.freItems) {
        viewModel.freItems = db.freItems.filter { it.fre_id eq  viewModel.fre.id }.toList().toMutableStateList()
    }

}


@Composable
private fun MyTopBar() = TopAppBar(
    title = { Text(text = if (viewModel.fre.type == "销售") "财政销售收入" else "财政采购支出") },
    navigationIcon = {
        IconButton(
            onClick = { frePageState = FrePageState.List },
            content = { Icon(painterResource("arrow_back_white_24dp.svg"), null) }
        )
    },
    actions = {
        IconButton(
            onClick = {
                DashboardViewModel.openDialog(
                    title = "确定删除",
                    message = "是否确定删除财政收支记录（${viewModel.fre.id}）？",
                    onConfirm = {  }
                )
            },
            content = { Icon(painterResource("delete_white_24dp.svg"), null) }
        )
    }
)