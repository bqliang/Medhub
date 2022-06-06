package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.SupplierSearchCondition
import model.database.Supplier

object SupplierListPageViewModel {

    var searchKeyword by mutableStateOf("")

    var supplierSearchCondition by mutableStateOf(SupplierSearchCondition.NAME)

    var conditionMenuExpanded by mutableStateOf(false)

    val suppliers: SnapshotStateList<Supplier> = SnapshotStateList()

    fun refresh(list: List<Supplier>) {
        suppliers.clear()
        suppliers.addAll(list)
    }
}