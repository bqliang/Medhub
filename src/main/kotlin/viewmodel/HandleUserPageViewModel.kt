package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import model.UserPageState
import model.database.User
import userPageState
import java.time.LocalDate

object HandleUserPageViewModel {
    var id by mutableStateOf(-1)
    var name by mutableStateOf("")
    var phone by mutableStateOf("")
    var sex by mutableStateOf("男")
    var idNum by mutableStateOf("")
    var type by mutableStateOf("")
    var entry: LocalDate by mutableStateOf(LocalDate.now())

    var sexMenuExpanded by mutableStateOf(false)
    var positionMenuExpanded by mutableStateOf(false)

    fun startHandleUserPage(user: User? = null){
        if (user == null){
            name = ""
            phone = ""
            sex = "男"
            idNum = ""
            type = "员工"
            entry = LocalDate.now()
            userPageState = UserPageState.Add
        } else {
            id = user.id
            name = user.name
            phone = user.phone
            sex = user.sex
            idNum = user.idNum
            type = user.type
            entry = user.entry
            userPageState = UserPageState.Edit
        }
    }
}