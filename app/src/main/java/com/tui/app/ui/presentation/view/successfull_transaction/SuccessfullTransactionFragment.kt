package com.tui.app.ui.presentation.view.successfull_transaction

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.tui.app.R
import com.tui.app.ui.BaseApplication
import com.tui.app.ui.presentation.view.currency_selection.CurrencySelectionViewModal
import com.tui.app.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SuccessfullTransactionFragment : Fragment() {

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

    Log.e("TAG", "MainContent: ", )
    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        ForwardIcon(viewModel)
        TickIcon()
        Message(viewModel)
    }


}

@Composable
fun ForwardIcon(viewModel: CurrencySelectionViewModal) {

    val navController = LocalView.current.findNavController()
    Box(modifier = Modifier.fillMaxWidth().padding(20.dp), contentAlignment = Alignment.TopEnd){

        Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "tick", modifier = Modifier.size(30.dp).clickable {
            viewModel.selectCurrentModal.value.clear()
            navController.popBackStack(R.id.currencySelectionFragment,inclusive = false)
        } )
    }
}

@Composable
fun TickIcon(){
    Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "tick", modifier = Modifier.padding(top = 50.dp).size(150.dp), tint = Color.Green)
}



@Composable
fun Message(viewModel: CurrencySelectionViewModal) {
    Column(modifier =  Modifier.fillMaxHeight().fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Great now you have ${viewModel.selectCurrentModal.value.conversionAmount} ${viewModel.selectCurrentModal.value.toCurrency} " +
                    "in your account.",
            modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp),
            color = MaterialTheme.colors.onSurface,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Your conversion rate was 1/${viewModel.selectCurrentModal.value.rate}",
            modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp),
            color = MaterialTheme.colors.onSurface,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

    }
}
