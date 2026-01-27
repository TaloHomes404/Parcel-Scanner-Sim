package wolf.north.parcelscannerapp.comps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import wolf.north.parcelscannerapp.mvvm.Model.files.Form
import wolf.north.parcelscannerapp.mvvm.Model.files.Package

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackageBottomBarResults(
    packageData: Package,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    onShare: () -> Unit,
    onRescan: () -> Unit
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true // Fully expanded bottom bar after scan
    )

    //Material theme val
    val colorScheme = MaterialTheme.colorScheme

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = colorScheme.surfaceContainerLowest,
        scrimColor = colorScheme.scrim.copy(alpha = 0.32f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        ) {

            Text(
                text = "Parcel Info",
                style = MaterialTheme.typography.titleMedium,
                color =  colorScheme.onSurface

            )

            Spacer(modifier = Modifier.height(20.dp))

            InfoRow("Number of parcel (ID)", packageData.trackingNumber)
            Spacer(modifier = Modifier.height(12.dp))

            InfoRow("Courier", packageData.courier.name)
            Spacer(modifier = Modifier.height(12.dp))

            InfoRow("Date of scan", packageData.scanDate)
            Spacer(modifier = Modifier.height(12.dp))

            InfoRow("Weight category", packageData.weightClass.name)
            Spacer(modifier = Modifier.height(12.dp))

            InfoRow("Parcel Type", packageData.shipmentType.name)

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onSave,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Save")
                }

                OutlinedButton(
                    onClick = onShare,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Share")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = onRescan,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Retake")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormBottomBarResults(
    form: Form,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    onShare: () -> Unit,
    onEditAgain: () -> Unit
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true // Fully expanded bottom bar after scan
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Data From Form",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            form.firstName?.let {
                InfoRow("First Name", it)
                Spacer(modifier = Modifier.height(12.dp))
            }

            form.lastName?.let {
                InfoRow("Last Name", it)
                Spacer(modifier = Modifier.height(12.dp))
            }

            form.date?.let {
                InfoRow("Date", it)
                Spacer(modifier = Modifier.height(12.dp))
            }

            form.id?.let {
                InfoRow("ID", it)
                Spacer(modifier = Modifier.height(12.dp))
            }

            form.address?.let {
                InfoRow("Address", it)
                Spacer(modifier = Modifier.height(12.dp))
            }

            form.phoneNumber?.let {
                InfoRow("Phone Number", it)
                Spacer(modifier = Modifier.height(12.dp))
            }

            form.email?.let {
                InfoRow("Email", it)
                Spacer(modifier = Modifier.height(12.dp))
            }

            form.additionalNotes?.let {
                InfoRow("Additional Notes", it)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onSave,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Save")
                }

                OutlinedButton(
                    onClick = onShare,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Share")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = onEditAgain,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Retake Scan")
            }
        }
    }
}


@Composable
fun InfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {

    val colorScheme = MaterialTheme.colorScheme

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = colorScheme.onSurface
        )
    }
}