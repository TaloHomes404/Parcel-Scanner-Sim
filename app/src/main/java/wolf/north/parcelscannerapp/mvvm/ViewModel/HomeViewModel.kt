package wolf.north.parcelscannerapp.mvvm.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import wolf.north.parcelscannerapp.mvvm.View.Home.HomeUiState
import wolf.north.parcelscannerapp.mvvm.View.Home.ScanType

class HomeViewModel : ViewModel(){

    //vals

    //ui state values in viewmodel
    private val _uiState = mutableStateOf(HomeUiState())
    val uiState: State<HomeUiState> = _uiState


    //methods

    //Method used after clicking Package button in Home Screen
    fun onPackageSelected(){
        _uiState.value = _uiState.value.copy(
            selectedScanType = ScanType.PACKAGE
        )
    }

    //Method used after clicking Form button in Home Screen
    fun onFormSelected(){
        _uiState.value = _uiState.value.copy(
            selectedScanType = ScanType.FORM
        )
    }

    //Method to load Scanner
    //Depends on type selected
    fun onScanSelected(){
        //TODO: scanner type select -> run scanner
    }

    //**
    //Navigation
    //**
    fun onHistoryClicked(
        navigate: () -> Unit
    ){
        navigate()
    }
}