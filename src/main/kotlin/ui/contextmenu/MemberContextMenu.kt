package ui.contextmenu

import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import model.database.Member
import viewmodel.CheckoutPageViewModel

@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
fun MemberContextMenu(member: Member, content: @Composable () -> Unit)
        = ContextMenuArea(
    items = {
        listOf(
            ContextMenuItem("创建销售结算单") { CheckoutPageViewModel.openCheckoutWindow(member) }
        )
    },
    content = content
)