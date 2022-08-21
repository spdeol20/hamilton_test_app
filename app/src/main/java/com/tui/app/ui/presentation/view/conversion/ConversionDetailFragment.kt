package com.tui.app.ui.presentation.view.conversion

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.tui.app.ui.BaseApplication
import com.tui.app.ui.presentation.view.currency_selection.CurrencySelectionViewModal
import com.tui.app.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import javax.inject.Inject

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ConversionDetailFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication
//    val viewModel = ViewModelProvider(this,ViewModelProvider.Factory.from(ViewModelInitializer(CurrencySelectionViewModal::class.java)))[CurrencySelectionViewModal::class.java]

//    private val  viewModel = ViewModelProvider(this)[CurrencySelectionViewModal::class.java]


    val viewModel by activityViewModels<CurrencySelectionViewModal>()
    val detailViewModal by activityViewModels<ConversionViewModal>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var controller  = findNavController()
        detailViewModal.navController = controller;
        detailViewModal.selectionViewmodel = viewModel
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme(darkTheme = false) {
                    Surface(color = MaterialTheme.colors.background) {
                        MainContent(viewModel,detailViewModal)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainContent(viewModel: CurrencySelectionViewModal, detailViewModal: ConversionViewModal) {

    val navController = LocalView.current.findNavController()
    val dialog by  detailViewModal.dialogModal.collectAsState()
    Log.e("TAG", "MainContent: ", )
    Box(modifier = Modifier){


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(modifier = Modifier
            .padding(all = 20.dp)
            .fillMaxWidth(), contentAlignment = Alignment.TopStart) {

            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "back",
                modifier = Modifier.clickable {
                    navController.popBackStack()
                })
        }
        Box(modifier = Modifier.size(100.dp))
        Text(
            text = "${viewModel.selectCurrentModal.value.amount} ${viewModel.selectCurrentModal.value.fromCurrency}  ",
            fontSize = 40.sp
        )
        Text(text = "precedes", modifier = Modifier, fontSize = 15.sp)
        Text(
            text = "${viewModel.selectCurrentModal.value.conversionAmount} ${viewModel.selectCurrentModal.value.toCurrency}  ",
            fontSize = 40.sp
        )
        ButtonCompose(viewModel = viewModel,detailViewModal)
    }
    if (dialog.showDialog){
        Dialog(onDismissRequest = {  }, content = dialog.content, properties = DialogProperties(usePlatformDefaultWidth = false))
    }
}

}

@Composable
fun ButtonCompose(viewModel: CurrencySelectionViewModal, detailViewModal: ConversionViewModal) {
    val navController = LocalView.current.findNavController()
    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxHeight()) {
        Column {
            TimerCompose(detailViewModal)
            Button(
                onClick = {
                    //your onclick code
                    detailViewModal.showApproveDialog(viewModel,navController)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                modifier = Modifier
                    .padding(top = 10.dp, start = 30.dp, end = 30.dp, bottom = 80.dp)
                    .height(50.dp)
                    .fillMaxWidth()
            )

            {
                Text(text = "Convert", color = Color.White)
            }
        }

    }

}
@Composable
fun TimerCompose(detailViewModal: ConversionViewModal) {
    val ticks by detailViewModal.minutes.collectAsState()
    LaunchedEffect(Unit) {
        Log.e("TAG", "TimerCompose: launched effect ", )
         detailViewModal.startTimer()
    }

    DisposableEffect(Unit) { onDispose {
        detailViewModal.stopTimer()
        Log.e("TAG", "TimerCompose: DisposableEffect effect ", )
    } }
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Icon(imageVector = Icons.Filled.Notifications, contentDescription = "timer")
        Text(text = "$ticks", fontSize = 35.sp, style = MaterialTheme.typography.h5)
        Text(text = " sec left", fontSize = 18.sp, style = MaterialTheme.typography.h5)
    }
}