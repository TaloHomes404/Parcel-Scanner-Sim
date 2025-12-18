package wolf.north.parcelscannerapp.mvvm.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import wolf.north.parcelscannerapp.mvvm.View.Home.HomeUiState
import wolf.north.parcelscannerapp.mvvm.View.Home.ScanType

class HomeViewModel : ViewModel(){

    //vals
    private val _uiState = mutableStateOf(HomeUiState())
    val uiState: State<HomeUiState> = _uiState


    //methods
    fun onPackageSelected(){
        _uiState.value = _uiState.value.copy(
            selectedScanType = ScanType.PACKAGE
        )
    }

    fun onFormSelected(){
        _uiState.value = _uiState.value.copy(
            selectedScanType = ScanType.FORM
        )
    }

    fun onScanSelected(){
        //TODO: scanner type select -> run scanner
    }


}