package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import model.database.Member
import java.time.LocalDate

object HandleMemberPageViewModel {

    var id by mutableStateOf(-1)
    var name by mutableStateOf("")
    var phone by mutableStateOf("")
    var sex by mutableStateOf("男")
    var regdate by mutableStateOf(LocalDate.now())


    var sexMenuExpanded by mutableStateOf(false)

    fun setData(member: Member? = null) {
        if (member == null) {
            id = -1
            name = ""
            phone = ""
            sex = "男"
            regdate = LocalDate.now()
        } else {
            id = member.id
            name = member.name
            phone = member.phone
            sex = member.sex
            regdate = member.regdate
        }
    }
}
