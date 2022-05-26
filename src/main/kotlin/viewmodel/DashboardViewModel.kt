package viewmodel

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import model.RailBarItem

object DashboardViewModel {

    var selectRailItem by mutableStateOf(RailBarItem.MEDICINE)

    val snackbarHostState by mutableStateOf(SnackbarHostState())

    var showDialog by mutableStateOf(false)
    var dialogTitle by mutableStateOf("")
    var dialogMessage by mutableStateOf("")
    var dialogOnConfirm: () -> Unit = { }

    fun openDialog(title: String, message: String, onConfirm: () -> Unit) {
        dialogTitle = title
        dialogMessage = message
        dialogOnConfirm = onConfirm
        showDialog = true
    }
}