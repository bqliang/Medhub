package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import checkoutWindowState
import kotlinx.coroutines.launch
import model.CheckoutPageState
import model.RailBarItem
import model.database.Medicine
import model.database.Member
import model.database.Stakeholder
import model.database.Supplier
import scope

object CheckoutPageViewModel {
    var list = mutableStateListOf<Medicine>()

    var member: Member? by mutableStateOf(null)
    var supplier: Supplier? by mutableStateOf(null)


    fun openCheckoutWindow(freStakeholder: Stakeholder) {
        if (checkoutWindowState != CheckoutPageState.Invisible) {
            scope.launch {
                DashboardViewModel.snackbarHostState
                    .showSnackbar(message = "不能同时打开多个结算窗口", actionLabel = "好的")
            }
            return
        }

        if (freStakeholder is Member) {
            member = freStakeholder
            checkoutWindowState = CheckoutPageState.Sales
            DashboardViewModel.selectRailItem = RailBarItem.MEDICINE
        } else if (freStakeholder is Supplier) {
            supplier = freStakeholder
            checkoutWindowState = CheckoutPageState.Purchase
            DashboardViewModel.selectRailItem = RailBarItem.MEDICINE
        }
    }


    fun closeCheckoutWindow() {
        list.clear()
        checkoutWindowState = CheckoutPageState.Invisible
    }
}