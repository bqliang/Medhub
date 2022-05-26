package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import db
import logic.searchFre
import model.FreType
import model.database.Fre
import model.database.fres
import org.ktorm.entity.toList

object FreListViewModel {

    var searchKeyword by mutableStateOf("")

    var showTypeMenu by mutableStateOf(false)

    val fres: SnapshotStateList<Fre> by lazy {
        searchFre()
        listOf<Fre>().toMutableStateList()
    }

    fun refresh(list: List<Fre>) {
        fres.clear()
        fres.addAll(list)
    }

    var fresType by mutableStateOf(FreType.All)

}