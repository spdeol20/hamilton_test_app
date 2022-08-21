package com.tui.app.ui.presentation.view.conversion

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.tui.app.R
import com.tui.app.network.modal.DialogModal
import com.tui.app.ui.presentation.component.CustomApproveDialog
import com.tui.app.ui.presentation.component.TimeOutDialog
import com.tui.app.ui.presentation.view.currency_selection.CurrencySelectionViewModal
import com.tui.app.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class  ConversionViewModal @Inject constructor(
    private val repository: ConversionRepository): ViewModel() {


    lateinit var selectionViewmodel: CurrencySelectionViewModal
    lateinit var navController: NavController
    var dialogModal = MutableStateFlow(DialogModal())
    private var timer: CountDownTimer? = null

    private var totalTime : Long = Utils.TIMEOUT_DURATION
    private val _minutes = MutableStateFlow(totalTime)
    val minutes: MutableStateFlow<Long> get() =  _minutes
    fun startCountDown() {

        timer = object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisecs: Long) {
                // Minutes
                val minutes = (millisecs / 1000)
                _minutes.value = (minutes)

                Log.e("TAG", "TimerCompose: startCountDown $minutes ", )
            }

            override fun onFinish() {
                //...countdown completed
                Log.e("TAG", "TimerCompose: onFinish $minutes ", )

                dialogModal.value = DialogModal(showDialog = false)
                showTimeoutDialog()
            }
        }

    }

    fun startTimer() {
        startCountDown()
        timer?.start()
    }

    fun stopTimer() {
        timer?.cancel()
    }


    fun showTimeoutDialog( ) {
        dialogModal.value = DialogModal(showDialog = true, content = {
            TimeOutDialog(
                onSelect = {flag ->
                    if (flag){
                        // approve
                        dialogModal.value = DialogModal(showDialog = false)
                        selectionViewmodel.selectCurrentModal.value.clear()
                        navController.popBackStack(R.id.currencySelectionFragment,inclusive = false)
                    }
                }
            )
        })
    }
    fun showApproveDialog(viewModel: CurrencySelectionViewModal, navController: NavController) {
        dialogModal.value = DialogModal(showDialog = true, content = {
            CustomApproveDialog(
                onSelect = {flag ->
                    if (flag){
                        // approve
                        dialogModal.value = DialogModal(showDialog = false)
                        navController.navigate(R.id.successfullTransactionFragment)
                    }else{
//                        cancel
                        dialogModal.value = DialogModal(showDialog = false)
                    }
                },viewModel
            )
        })
        Log.e("TAG", "showApproveDialog: ${dialogModal.value.showDialog}", )
    }

    fun timeOut() {

    }

}