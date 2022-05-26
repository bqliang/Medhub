package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import model.database.Supplier

object HandleSupplierPageViewModel {

    var id by mutableStateOf(-1)
    var name by mutableStateOf("")
    var address by mutableStateOf("")
    var phone by mutableStateOf("")
    var email by mutableStateOf("")

    fun setData(supplier: Supplier? = null) {
        if (supplier == null) {
            id = -1
            name = ""
            address = ""
            phone= ""
            email= ""
        } else {
            id = supplier.id
            name = supplier.name
            address = supplier.address
            phone = supplier.phone
            email = supplier.email?:""
        }
    }
}