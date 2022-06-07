package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import db
import frePageState
import model.FrePageState
import model.database.Fre
import model.database.FreItem
import model.database.freItems
import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.toList

object FreDetailsPageViewmodel {
    var fre: Fre by mutableStateOf(Fre { })
    var freItems: SnapshotStateList<FreItem> = SnapshotStateList()


    fun startFreDetailsPage(fre: Fre) {
        this.fre = fre
        this.freItems.clear()
        this.freItems.addAll(db.freItems.filter { it.fre_id eq  fre.id }.toList())
        frePageState = FrePageState.Details
    }
}