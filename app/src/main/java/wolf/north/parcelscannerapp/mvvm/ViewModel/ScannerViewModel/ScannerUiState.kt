package wolf.north.parcelscannerapp.mvvm.ViewModel.ScannerViewModel

data class ScannerUiState(
    val isProcessing: Boolean = false,
    val scanResult: String? = null
)