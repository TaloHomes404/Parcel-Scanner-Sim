package wolf.north.parcelscannerapp.mvvm.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import wolf.north.parcelscannerapp.mvvm.model.files.Form
import wolf.north.parcelscannerapp.mvvm.view.home.HomeUiState
import wolf.north.parcelscannerapp.mvvm.view.home.ScanType
import wolf.north.parcelscannerapp.repository.ScanRepository
import wolf.north.parcelscannerapp.repository.UserSessionRepository


class HomeViewModel : ViewModel(){

    //vals
    //ui state values in viewmodel
    private val _uiState = mutableStateOf(HomeUiState())
    val uiState: State<HomeUiState> = _uiState

    //Singleton values set to get user/session flow for home screen
    val currentUser = UserSessionRepository.currentUser
    val currentSession = UserSessionRepository.currentSession


    //Connecting values with repository
    val packages = ScanRepository.packages.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val forms = ScanRepository.forms.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )


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