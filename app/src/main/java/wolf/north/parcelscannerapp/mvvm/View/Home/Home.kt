package wolf.north.parcelscannerapp.mvvm.View.Home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import wolf.north.parcelscannerapp.comps.scanners.FormScannerScreen
import wolf.north.parcelscannerapp.comps.scanners.PackageScannerScreen
import wolf.north.parcelscannerapp.mvvm.ViewModel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
    onGoToHistory: () -> Unit
) {

    //Home screen vals
    //State from HomeUiState hoisted in screen
    val state by viewModel.uiState

    //State value for scanner visibility
    var showScanner by remember { mutableStateOf(false) }

    if(showScanner) {
        when(state.selectedScanType) {
            ScanType.FORM -> FormScannerScreen()
            ScanType.PACKAGE -> PackageScannerScreen()
            else -> PackageScannerScreen()
        }
        return
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Parcel Scanner") }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { viewModel.onHistoryClicked { onGoToHistory }},
                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) }
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Package Button
                        ScanTypeCard(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.Inventory2,
                            label = "Package",
                            isSelected = state.selectedScanType == ScanType.PACKAGE,
                            onClick = { viewModel.onPackageSelected() }
                        )

                        // Form Button
                        ScanTypeCard(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.Description,
                            label = "Form",
                            isSelected = state.selectedScanType == ScanType.FORM,
                            onClick = { viewModel.onFormSelected() }
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text("Click to capture",
                        fontSize = 24.sp,
                        color = if (state.selectedScanType != null) Color.Black else Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Open scanner",
                        modifier = Modifier
                            .size(106.dp)
                            .clickable(enabled = state.selectedScanType != null) {
                                showScanner = true
                            },
                        tint = if (state.selectedScanType != null) Color.Black else Color.Gray
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen( onGoToHistory = { null } )
}

@Composable
fun ScanTypeCard(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(140.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = if (isSelected) {
            BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        } else {
            BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(48.dp),
                tint = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}