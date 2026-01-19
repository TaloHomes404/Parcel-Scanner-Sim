package wolf.north.parcelscannerapp.mvvm.ViewModel.ScannerViewModel

import wolf.north.parcelscannerapp.mvvm.Model.files.Form
import wolf.north.parcelscannerapp.mvvm.Model.files.Package

data class ScannerUiState(
    val isProcessing: Boolean = false,
    val scanResult: String? = null,
    val scannedPackage: Package? = null,
    val scannedForm: Form? = null
)