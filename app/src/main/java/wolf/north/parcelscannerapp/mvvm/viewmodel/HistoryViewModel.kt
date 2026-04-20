package wolf.north.parcelscannerapp.mvvm.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import wolf.north.parcelscannerapp.mvvm.model.files.Form
import wolf.north.parcelscannerapp.mvvm.model.files.Package
import wolf.north.parcelscannerapp.repository.ScanRepository

data class HistoryUiState(
    val selectedPackage: Package? = null,
    val selectedForm: Form? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)

class HistoryViewModel : ViewModel() {

    //**
    //Viewmodel vals
    //**
    //UI State handle
    private val _isLoading = mutableStateOf(false)
    val isLoading = _isLoading

    // DATA Call
    val packages = ScanRepository.packages
    val forms = ScanRepository.forms
    val errorMessage = ScanRepository.errorMessage

    //UI State private set
    var uiState = mutableStateOf(HistoryUiState())
        private set

    //Init method to call on creating viewmodel state (entering screen handler)
    init {
        refreshData()
    }

    //Method for calling coroutine to do API Call and set in-viewmodel values
    fun refreshData() {
        viewModelScope.launch {
            _isLoading.value = true
            ScanRepository.fetchAllData()
            _isLoading.value = false
        }
    }

    //Display package info from selected data
    fun showPackageDetails(packageData: Package) {
        uiState.value = uiState.value.copy(selectedPackage = packageData)
    }

    fun showFormDetails(form: Form) {
        uiState.value = uiState.value.copy(selectedForm = form)
    }

    //Method helper to close bottom sheet
    fun dismissDetails() {
        uiState.value = HistoryUiState()
    }

    //TODO: Renew delete methods and add DELETE calls in API
    /*fun deletePackage(packageData: Package) {
        ScanRepository.deletePackage(packageData)
        dismissDetails()
    }*/

   /* fun deleteForm(form: Form) {
        ScanRepository.deleteForm(form)
        dismissDetails()
    }*/

    //**
    // Navigation
    //**

    fun goHomeClicked(
        navigate: () -> Unit
    ){
        navigate()
    }

}