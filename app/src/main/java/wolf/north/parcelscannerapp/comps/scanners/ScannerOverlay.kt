package wolf.north.parcelscannerapp.comps.scanners

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PackageScannerOverlay(
    modifier: Modifier = Modifier,
    isProcessing: Boolean,
    onCapture: () -> Unit
) {

    //Material theme value
    val colorScheme = MaterialTheme.colorScheme

    Box(modifier = modifier.fillMaxSize()) {
        //tint box for overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(colorScheme.scrim.copy(alpha = 0.35f))
        )

        //Layout frame
        Box(
            modifier = Modifier
                .size(width = 290.dp, height = 180.dp)
                .align(Alignment.Center)
                .border(2.dp, colorScheme.primary, RoundedCornerShape(8.dp))
                .background(Color.Transparent)
        )

        // Upper screen tip (can change)
        Text(
            text = "Wyrównaj etykietę w ramce i naciśnij Skanuj",
            color = colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 48.dp)
        )

        // capture button
        FloatingActionButton(
            onClick = {
                if (!isProcessing) onCapture()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            containerColor = if (isProcessing) colorScheme.onSurface.copy(alpha = 0.12f) else colorScheme.primary
        ) {
            if (isProcessing) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Capture",
                    tint = colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun FormScannerOverlay(
    modifier: Modifier = Modifier,
    isProcessing: Boolean,
    onCapture: () -> Unit
) {

    val colorScheme = MaterialTheme.colorScheme

    Box(modifier = modifier.fillMaxSize()) {
        //tint box for overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(colorScheme.scrim.copy(alpha = 0.35f))
        )

        //Layout frame
        Box(
            modifier = Modifier
                .size(width = 290.dp, height = 390.dp)
                .align(Alignment.Center)
                .border(2.dp, color = colorScheme.tertiary, RoundedCornerShape(12.dp))
                .background(Color.Transparent)
        )

        // Upper screen tip (can change)

        Text(
            text = "Dopasuj formularz do ramki",
            color = colorScheme.onTertiaryContainer,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 48.dp)
        )

        // capture button

        FloatingActionButton(
            onClick = {
                if (!isProcessing) onCapture()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            containerColor = if (isProcessing) colorScheme.onSurface.copy(alpha = 0.12f) else colorScheme.primary
        ) {
            if (isProcessing) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Zrób zdjęcie",
                    tint = colorScheme.onPrimary
                )
            }
        }
    }
}