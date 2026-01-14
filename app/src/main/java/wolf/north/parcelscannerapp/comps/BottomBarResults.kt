package wolf.north.parcelscannerapp.comps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = "Parcel Info",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            InfoRow("Number of parcel (ID)", packageData.trackingNumber)
            Spacer(modifier = Modifier.height(8.dp))

            InfoRow("Courier", packageData.courier.name)
            Spacer(modifier = Modifier.height(8.dp))

            InfoRow("Date of scan", packageData.scanDate)
            Spacer(modifier = Modifier.height(8.dp))

            InfoRow("Weight category", packageData.weightClass.name)
            Spacer(modifier = Modifier.height(8.dp))

            InfoRow("Parcel Type", packageData.shipmentType.name)

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
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

            Spacer(modifier = Modifier.height(8.dp))

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
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = "Data From Form",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            form.firstName?.let {
                InfoRow("First Name", it)
                Spacer(modifier = Modifier.height(8.dp))
            }

            form.lastName?.let {
                InfoRow("Last Name", it)
                Spacer(modifier = Modifier.height(8.dp))
            }

            form.date?.let {
                InfoRow("Date", it)
                Spacer(modifier = Modifier.height(8.dp))
            }

            form.id?.let {
                InfoRow("ID", it)
                Spacer(modifier = Modifier.height(8.dp))
            }

            form.address?.let {
                InfoRow("Address", it)
                Spacer(modifier = Modifier.height(8.dp))
            }

            form.phoneNumber?.let {
                InfoRow("Phone Number", it)
                Spacer(modifier = Modifier.height(8.dp))
            }

            form.email?.let {
                InfoRow("Email", it)
                Spacer(modifier = Modifier.height(8.dp))
            }

            form.additionalNotes?.let {
                InfoRow("Additional Notes", it)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
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

            Spacer(modifier = Modifier.height(8.dp))

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
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}