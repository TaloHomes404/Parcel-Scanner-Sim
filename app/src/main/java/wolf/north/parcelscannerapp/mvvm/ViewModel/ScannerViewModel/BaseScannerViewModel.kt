package wolf.north.parcelscannerapp.mvvm.ViewModel.ScannerViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State

open class BaseScannerViewModel : ViewModel(){

    //
    //vals
    //

    //UI state hoisting in viewmodel vals
    private val _uiState = mutableStateOf(ScannerUiState())
    val uiState: State<ScannerUiState> = _uiState


    //
    //Methods for package scanner
    //

    //Capture button functionality
    //UI state changes
    fun onCaptureClicked(){
        _uiState.value = _uiState.value.copy(isProcessing = true)
        //TODO: ML kit włączanie aparatu
    }

    fun onProcessingFinished(result: String){
        _uiState.value = _uiState.value.copy(
            isProcessing = false,
            scanResult = result
        )
    }

    fun onDismissResult(){
        _uiState.value = _uiState.value.copy(scanResult = null)
    }

    fun onRetry(){
        _uiState.value = ScannerUiState()
    }



}