package ui.contextmenu

import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import model.database.Supplier
import viewmodel.CheckoutPageViewModel


@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
fun SupplierContextMenu(supplier: Supplier, content: @Composable () -> Unit)
        = ContextMenuArea(
    items = {
        listOf(
            ContextMenuItem("创建采购结算单") { CheckoutPageViewModel.openCheckoutWindow(supplier) }
        )
    },
    content = content
)