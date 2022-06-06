package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.FreType
import model.database.Fre

object FreListViewModel {

    var searchKeyword by mutableStateOf("")

    var showTypeMenu by mutableStateOf(false)

    val fres: SnapshotStateList<Fre> = SnapshotStateList()

    fun refresh(list: List<Fre>) {
        fres.clear()
        fres.addAll(list)
    }

    var fresType by mutableStateOf(FreType.All)

}