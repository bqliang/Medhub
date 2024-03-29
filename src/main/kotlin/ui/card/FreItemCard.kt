package ui.card

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.database.FreItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FreItemCard(freItem: FreItem) = Card(
    elevation = 4.dp,
    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
    onClick = {  }
) {
    Box(modifier = Modifier.padding(16.dp).wrapContentHeight()) {

        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = "${freItem.medicine.name}(${freItem.medicine.id})",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("%.2f  *  %d".format(freItem.price, freItem.quantity))
            Spacer(modifier = Modifier.width(12.dp))
            Text("  =  %.2f".format(freItem.subTotal))
        }
    }
}