package ui.page

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import checkoutWindowState
import logic.addFre
import model.CheckoutPageState
import ui.card.CheckoutCard
import ui.view.ScrollToTopBtn
import viewmodel.CheckoutPageViewModel

private val viewModel = CheckoutPageViewModel

@Composable
fun CheckoutPage() = Column(modifier = Modifier.fillMaxSize()) {
    val medicines = CheckoutPageViewModel.list.sortedBy { it.addTime }
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
            items(medicines) {
                CheckoutCard(it)
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
            text = "总金额：%.2f".format(viewModel.list.sumOf { it.subTotal.toDouble() }),
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = if (checkoutWindowState == CheckoutPageState.Sales)
                "客户：${viewModel.member!!.name}（${viewModel.member!!.id}）"
            else "供应商：${viewModel.supplier!!.name}（${viewModel.supplier!!.id}）"
        )
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = { addFre() }
        ) {
            Icon(
                painter = painterResource("save_white_24dp.svg"),
                contentDescription = null
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("保存")
        }
    }

}