package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.UserSearchCondition
import model.database.User


object UserListPageViewModel {

    /**
     * 搜索框的关键字
     */
    var searchKeyword by mutableStateOf("")

    /**
     * 搜索筛选条件（根据什么去搜索用户）
     * 可选值：工号、姓名、电话
     */
    var userSearchCondition by mutableStateOf(UserSearchCondition.NAME)

    /**
     * 搜索筛选条件菜单是否展开
     */
    var conditionMenuExpanded by mutableStateOf(false)

    /**
     * 用户列表
     */
    val users: SnapshotStateList<User> = SnapshotStateList()

    fun refresh(list: List<User>) {
        users.clear()
        users.addAll(list)
    }
}