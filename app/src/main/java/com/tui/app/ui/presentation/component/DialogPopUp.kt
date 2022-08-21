package com.tui.app.ui.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tui.app.ui.presentation.view.currency_selection.CurrencySelectionViewModal


@Composable
fun CustomApproveDialog(
    onSelect: (flag: Boolean) -> Unit,
    viewModel: CurrencySelectionViewModal,
) {
    Column(content = {
        Text(text = "Approval Required", fontSize = 22.sp, modifier = Modifier
            .padding(15.dp), style =   MaterialTheme.typography.h4)
        Text(text = "Your are about to get ${viewModel.selectCurrentModal.value.conversionAmount} ${viewModel.selectCurrentModal.value.toCurrency} for " +
                "${viewModel.selectCurrentModal.value.amount} ${viewModel.selectCurrentModal.value.fromCurrency}. Do you approve this transaction?", fontSize = 20.sp, modifier = Modifier
            .padding(bottom = 15.dp, start = 15.dp, end = 15.dp))
        Row( modifier = Modifier.padding(10.dp)) {
            Button(onClick = { onSelect(true) }  , modifier = Modifier
                .weight(1f)
                .height(40.dp)) {
                Text(text = "Approve")
            }
            Box(modifier = Modifier.width(10.dp))
            Button(onClick = {onSelect(false)  } , modifier = Modifier
                .weight(1f)
                .height(40.dp)) {
                Text(text = "Cancel")
            }
        }

    }, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .padding(10.dp)
        .wrapContentHeight()
        .fillMaxWidth()
        .background(
            Color.White
        )
        .padding(20.dp))


}
//commit
@Composable
fun TimeOutDialog(
    onSelect: (flag: Boolean) -> Unit,
) {
    Column(content = {
        Text(text = "Sorry you have timed out. Please start over again", fontSize = 20.sp, modifier = Modifier
            .padding(15.dp), style =   MaterialTheme.typography.h4)
         Button(onClick = { onSelect(true) }  , modifier = Modifier
        .height(40.dp)) {
        Text(text = "Start")
    }


    }, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .padding(10.dp)
        .wrapContentHeight()
        .fillMaxWidth()
        .background(
            Color.White
        )
        .padding(20.dp))


}
