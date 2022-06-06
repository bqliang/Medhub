package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import logic.searchMedicine
import model.MedicineSearchCondition
import model.database.Medicine

object MedicineListPageViewModel {

    var searchKeyword by mutableStateOf("")

    var medicineSearchCondition by mutableStateOf(MedicineSearchCondition.NAME)

    var conditionMenuExpanded by mutableStateOf(false)

    val medicines: SnapshotStateList<Medicine> by lazy {
        searchMedicine()
        listOf<Medicine>().toMutableStateList()
    }

    fun refresh(list: List<Medicine>) {
        medicines.clear()
        medicines.addAll(list)
    }
}