package wolf.north.parcelscannerapp.comps.scanners

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import wolf.north.parcelscannerapp.mvvm.ViewModel.ScannerViewModel.PackageScannerViewModel

@Composable
fun PackageScanner(
    modifier: Modifier = Modifier,
    viewModel: PackageScannerViewModel = viewModel()
) {

    //Package screen vals
    //state
    val state by viewModel.uiState


}