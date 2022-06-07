package viewmodel

import androidx.compose.runtime.snapshots.SnapshotStateList
import frePageState
import model.FrePageState
import model.database.Fre
import model.database.FreItem

object FreDetailsPageViewmodel {
    var fre: Fre = Fre { }
    var freItems: SnapshotStateList<FreItem> = SnapshotStateList()


    fun startFreDetailsPage(fre: Fre) {
        this.fre = fre
        frePageState = FrePageState.Details
    }
}