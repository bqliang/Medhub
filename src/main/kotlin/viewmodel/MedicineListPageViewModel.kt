package viewmodel

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import db
import logic.searchMedicine
import model.MedicineSearchCondition
import model.database.Medicine
import model.database.medicines
import org.ktorm.entity.toList

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