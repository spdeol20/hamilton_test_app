package com.tui.app.ui.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tui.app.ui.presentation.view.currency_selection.CurrencySelectionViewModal
import com.tui.app.utils.Utils
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppBottomSheetLayout(
    modifier: Modifier = Modifier,
    sheetContent:  @Composable () ColumnScope.() -> Unit  ,
    sheetState: ModalBottomSheetState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    content:  @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        sheetContent = sheetContent,
        modifier = modifier,
        sheetState = sheetState, sheetBackgroundColor = sheetBackgroundColor, content = content,
    )
}
data class AppBottomSheetModal(
    var sheetContent: @Composable ColumnScope.() -> Unit ={Text("bottomshet")},
)


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomBottomSheet(
    bottomSheetScaffoldState: ModalBottomSheetState,
    onSelect: (value: String) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    LazyColumn(content = {
        item {
            Text(text = "CLOSE", fontSize = 14.sp, modifier = Modifier
                .clickable {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.hide()
                    }
                }
                .padding(15.dp))

        }
        items(Utils.selectionList.size) { index ->

            Text(
                text = Utils.selectionList[index],
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable { onSelect(Utils.selectionList[index]) }
                    .fillMaxWidth()
                    .padding(20.dp)
            )
            Divider()
        }

    }, horizontalAlignment = Alignment.Start, modifier = Modifier.height(300.dp))
}
