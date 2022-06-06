package ui.card

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import medicinePageState
import model.MedicinePageState
import model.database.Medicine
import ui.contextmenu.MedicineContextMenu
import viewmodel.HandleMedicinePageViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MedicineCard(medicine: Medicine) = Card(
    elevation = 4.dp,
    onClick = {
        HandleMedicinePageViewModel.setData(medicine)
        medicinePageState = MedicinePageState.Edit
    },
    content = {
        MedicineContextMenu(medicine) {
            Box(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Column(modifier = Modifier.align(Alignment.TopStart)) {
                    Text(
                        text = medicine.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = medicine.code
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "厂家：${medicine.manufacturer}"
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "分类：${medicine.category.category.name} / ${medicine.category.name}"
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "库存：${medicine.inventory}"
                    )
                }
                Text(
                    modifier = Modifier.align(Alignment.TopEnd),
                    text = String.format("%08d", medicine.id),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Thin
                )
            }
        }
    }
)