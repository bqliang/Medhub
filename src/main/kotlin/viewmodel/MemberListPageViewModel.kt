package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.MemberSearchCondition
import model.database.Member

object MemberListPageViewModel {

    var searchKeyword by mutableStateOf("")
    var memberSearchCondition by mutableStateOf(MemberSearchCondition.NAME)
    var conditionMenuExpanded by mutableStateOf(false)

    val members: SnapshotStateList<Member> = SnapshotStateList()

    fun refresh(list: List<Member>) {
        members.clear()
        members.addAll(list)
    }
}