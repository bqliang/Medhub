package ui.contextmenu

import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import checkoutWindowState
import kotlinx.coroutines.launch
import model.CheckoutPageState
import model.database.Medicine
import scope
import viewmodel.CheckoutPageViewModel
import viewmodel.DashboardViewModel

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun MedicineContextMenu(medicine: Medicine, content: @Composable () -> Unit) = ContextMenuArea(
    items = {
        listOf(
            ContextMenuItem("添加到结算单") {
                if (checkoutWindowState == CheckoutPageState.Invisible) {
                    scope.launch {
                        DashboardViewModel.snackbarHostState.showSnackbar(
                            message = "您必须先打开结算页",
                            actionLabel = "好的"
                        )
                    }
                    return@ContextMenuItem
                }
                val exist = CheckoutPageViewModel.list.find { it.id == medicine.id } != null
                if (!exist){
                    val med = medicine.copy()
                    med.apply {
                        addTime = System.currentTimeMillis()
                        checkoutQuantity = 1
                        CheckoutPageViewModel.list.add(this)
                    }
                }
            }
        )
    },
    content = content
)