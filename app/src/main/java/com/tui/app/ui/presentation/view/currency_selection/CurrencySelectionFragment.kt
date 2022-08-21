package com.tui.app.ui.presentation.view.currency_selection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.tui.app.R
import com.tui.app.ui.BaseApplication
import com.tui.app.ui.presentation.component.AppBottomSheetLayout
import com.tui.app.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CurrencySelectionFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication
//    private val viewModel: CurrencySelectionViewModal by viewModels()
    val viewModel by activityViewModels<CurrencySelectionViewModal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.hitToGetCurrency("USD")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme(darkTheme = false) {
                    Surface(color = MaterialTheme.colors.background) {
                        MainContent(viewModel)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(viewModel: CurrencySelectionViewModal) {
    var challenge = viewModel.allCurrencies.collectAsState()

    val sheetModal by viewModel.appSheetModal.collectAsState()
    Log.e("TAG", "MainContent: ", )
    AppBottomSheetLayout(sheetState = viewModel.appSheetState, sheetContent = sheetModal.sheetContent, content ={
        Column(modifier = Modifier) {
            CurrencySelectionCompose(  viewModel)
            Amount(viewModel)
            Button(viewModel)
        }
    })


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CurrencySelectionCompose(
    viewModel: CurrencySelectionViewModal
) {

    val coroutineScope = rememberCoroutineScope()
    val data = viewModel.selectCurrentModal.collectAsState()

    val focusManager = LocalFocusManager.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp), horizontalArrangement = Arrangement.Center
    ) {
        ///LEFT CURRENCY
        Row(
            modifier = Modifier
                .clickable {
                    focusManager.clearFocus()
                    viewModel.chooseFromConversionCurrency(coroutineScope)

                }
                .border(
                    border = ButtonDefaults.outlinedBorder,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(20.dp)

        ) {
            Text(text = data.value.fromCurrency, modifier = Modifier)
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "dropdown")
        }

        ///EXCHANGE ICONS
        Column(
            modifier = Modifier.width(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "arrow")
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "arrowback")
        }

        ///RIGHT CURRENCY
        Row(
            modifier = Modifier
                .clickable {
                    focusManager.clearFocus()
                    viewModel.chooseToConversionCurrency(coroutineScope)
                }
                .border(
                    border = ButtonDefaults.outlinedBorder,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(20.dp)
        ) {
            Text(text =  data.value.toCurrency, modifier = Modifier)
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "dropdown")
        }
    }
}

@Composable
fun Amount(viewModel: CurrencySelectionViewModal) {
    var text by remember { mutableStateOf(TextFieldValue( viewModel.selectCurrentModal.value.amount)) }
    Column(){
        Text(
            text = "Amount: ",
            modifier = Modifier.padding(top = 100.dp, start = 20.dp, end = 20.dp) ,
            color = MaterialTheme.colors.primary,
            fontSize = 18.sp
        )

        TextField(
            value = text,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .padding(start = 50.dp, end = 50.dp)
                .fillMaxWidth()
                .padding(top = 10.dp),
            onValueChange = { newText ->
                text = newText
                viewModel.selectCurrentModal.value.amount = text.text
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White
            ),
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colors.onSurface,
                fontSize = 50.sp,
                textAlign = TextAlign.Center
            ),

            )
    }
}

@Composable
fun Button(viewModel: CurrencySelectionViewModal) {
    val navController = LocalView.current.findNavController()
    Box(modifier = Modifier.fillMaxHeight(1f), contentAlignment = Alignment.BottomCenter){
        Button(
            onClick = {
                //your onclick code
                viewModel.calculateCurrency()
                navController.navigate(R.id.currencyDetailFragment)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            modifier = Modifier
                .padding(top = 100.dp, start = 30.dp, end = 30.dp, bottom = 80.dp)
                .height(50.dp)
                .fillMaxWidth()
        )

        {
            Text(text = "Calculate", color = Color.White)
        }
    }

}