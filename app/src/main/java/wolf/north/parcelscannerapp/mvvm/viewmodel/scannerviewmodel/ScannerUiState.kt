package wolf.north.parcelscannerapp.mvvm.viewmodel.scannerviewmodel

import wolf.north.parcelscannerapp.mvvm.model.files.Form
import wolf.north.parcelscannerapp.mvvm.model.files.Package

data class ScannerUiState(
    val isProcessing: Boolean = false,
    val scanResult: String? = null,
    val scannedPackage: Package? = null,
    val scannedForm: Form? = null
)