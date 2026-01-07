package wolf.north.parcelscannerapp.mvvm.ViewModel.ScannerViewModel

data class PackageScannerUiState(
    val isProcessing: Boolean = false,
    val scanResult: String? = null
)