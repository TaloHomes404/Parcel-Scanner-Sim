package wolf.north.parcelscannerapp.mvvm.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import wolf.north.parcelscannerapp.mvvm.Model.files.Form
import wolf.north.parcelscannerapp.mvvm.Model.files.Package
import wolf.north.parcelscannerapp.repository.ScanRepository

data class HistoryUiState(
    val selectedPackage: Package? = null,
    val selectedForm: Form? = null
)

class HistoryViewModel : ViewModel() {

    //**
    //Viewmodel vals
    //**

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

    var uiState = mutableStateOf(HistoryUiState())
        private set

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

    fun deletePackage(packageData: Package) {
        ScanRepository.deletePackage(packageData)
        dismissDetails()
    }

    fun deleteForm(form: Form) {
        ScanRepository.deleteForm(form)
        dismissDetails()
    }

}