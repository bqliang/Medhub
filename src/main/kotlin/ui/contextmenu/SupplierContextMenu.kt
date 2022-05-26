package ui.contextmenu

import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import model.database.Member
import model.database.Supplier


@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
fun SupplierContextMenu(supplier: Supplier, content: @Composable () -> Unit)
        = ContextMenuArea(
    items = {
        listOf(
            ContextMenuItem("创建支出订单") {
                // TODO 使用当前供应商创建支出订单
            }
        )
    },
    content = content
)