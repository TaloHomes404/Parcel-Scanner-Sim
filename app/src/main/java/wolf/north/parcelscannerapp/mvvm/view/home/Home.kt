package wolf.north.parcelscannerapp.mvvm.view.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import wolf.north.parcelscannerapp.comps.cards.ScannerCard
import wolf.north.parcelscannerapp.comps.cards.StatsCard
import wolf.north.parcelscannerapp.comps.items.RecentScanItem
import wolf.north.parcelscannerapp.comps.scanners.FormScannerScreen
import wolf.north.parcelscannerapp.comps.scanners.PackageScannerScreen
import wolf.north.parcelscannerapp.mvvm.viewmodel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
    onScannerVisibilityChanged: (Boolean) -> Unit = {}
) {

    val state by viewModel.uiState
    val packages by viewModel.packages.collectAsState()
    val forms by viewModel.forms.collectAsState()

    var showScanner by remember { mutableStateOf(false) }
    val colorScheme = MaterialTheme.colorScheme

    //Get current session from viewmodel singleton instance object
    val currentSession by viewModel.currentSession.collectAsState()

    LaunchedEffect(showScanner) {
        onScannerVisibilityChanged(showScanner)
    }
    
    if (showScanner) {
        when (state.selectedScanType) {
            ScanType.FORM -> FormScannerScreen(
                onNavigateBack = { showScanner = false }
            )
            ScanType.PACKAGE -> PackageScannerScreen(
                onNavigateBack = { showScanner = false }
            )
            else -> PackageScannerScreen(
                onNavigateBack = { showScanner = false }
            )
        }
        return 
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {

        // Package / Forms Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ScannerCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Inventory2,
                title = "Scan Package",
                description = "Scan package label",
                iconColor = Color(0xFF4CAF50),
                onClick = {
                    viewModel.onPackageSelected()
                    showScanner = true
                }
            )

            ScannerCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Description,
                title = "Scan Form",
                description = "Digitalize document",
                iconColor = Color(0xFF2196F3),
                onClick = {
                    viewModel.onFormSelected()
                    showScanner = true
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        StatsCard(
            scannedPackages = currentSession?.scannedPackages ?: 0,
            scannedForms = currentSession?.scannedForms ?: 0,
            inTransit = 0,
            onCardClick = {  }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Last scanned",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            TextButton(onClick = { /* TODO Nawigacja do historii */ }) {
                Text("See All")
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }


        Spacer(modifier = Modifier.height(8.dp))
        //Last scanned history
        LazyColumn(
            modifier = Modifier.height(350.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //  Packages
            items(packages) { packageData ->
                RecentScanItem(
                    icon = {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = colorScheme.primaryContainer,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.Inventory2,
                                    contentDescription = null,
                                    tint = colorScheme.primary
                                )
                            }
                        }
                    },
                    overlineText = packageData.courier.displayName,
                    mainText = packageData.trackingNumber,
                    subText = packageData.scanDate,
                    onClick = { }
                )
            }

            //  Forms
            items(forms) { formData ->
                RecentScanItem(
                    icon = {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = colorScheme.tertiaryContainer,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.Description,
                                    contentDescription = null,
                                    tint = colorScheme.tertiary
                                )
                            }
                        }
                    },
                    overlineText = "Formularz",
                    mainText = formData.getFullName(),
                    subText = formData.date ?: "Brak daty",
                    onClick = { }
                )
            }

            //  (Empty State)
            if (packages.isEmpty() && forms.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Outlined.History,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "No last scans in history",
                            style = MaterialTheme.typography.bodyMedium,
                            color = colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}



@Preview
@Composable
private fun HomeScreenContentPreview() {
    HomeScreenContent()
}
