package wolf.north.parcelscannerapp.comps.scanners

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import wolf.north.parcelscannerapp.mvvm.ViewModel.ScannerViewModel.BaseScannerViewModel

@Composable
fun PackageScanner(
    modifier: Modifier = Modifier,
    viewModel: BaseScannerViewModel = viewModel()
) {

    //Package screen vals
    //state
    val state by viewModel.uiState


}