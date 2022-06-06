package ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import checkoutWindowState
import model.CheckoutPageState
import model.database.Medicine
import viewmodel.CheckoutPageViewModel

private val viewModel = CheckoutPageViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CheckoutCard(medicine: Medicine) = Card(
    elevation = 4.dp,
    modifier = Modifier.fillMaxWidth().wrapContentHeight()
) {
    Box(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Column {
            Text(
                text = medicine.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text("药品编号：${medicine.id}")
            Spacer(modifier = Modifier.height(6.dp))
            Text("当前库存：${medicine.inventory}")
        }

        IconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = { viewModel.list.remove(medicine) }
        ) {
            Icon(painter = painterResource("close_FILL0_wght400_GRAD0_opsz24.svg"), null)
        }

        Row(
            modifier = Modifier.padding(end = 56.dp).align(Alignment.BottomEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "%.2f  *  ".format(
                    if (checkoutWindowState == CheckoutPageState.Sales) medicine.price else medicine.purchasePrice
                )
            )
            Spacer(modifier = Modifier.width(12.dp))
            BasicTextField(
                modifier = Modifier
                    .background(color = Color.Gray, shape = RoundedCornerShape(12.dp))
                    .padding(12.dp),
                value = medicine.checkoutQuantity.toString(),
                onValueChange = {
                    try {
                        val new = medicine.copy()
                        new.checkoutQuantity = it.toInt()
                        viewModel.list.remove(medicine)
                        viewModel.list.add(new)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                singleLine = true
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "  =  %.2f".format(medicine.subTotal))
        }
    }

}