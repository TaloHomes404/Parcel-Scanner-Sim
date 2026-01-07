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
import wolf.north.parcelscannerapp.mvvm.ViewModel.ScannerViewModel.ScannerUiState

@Composable
fun ScannerOverlay(
    modifier: Modifier = Modifier,
    isProcessing: Boolean,
    onCapture: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        //tint box for overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.35f))
        )

        //Layout frame
        Box(
            modifier = Modifier
                .size(width = 280.dp, height = 160.dp)
                .align(Alignment.Center)
                .border(2.dp, Color.Green, RoundedCornerShape(8.dp))
                .background(Color.Transparent)
        )

        // Upper screen tip (can change)
        Text(
            text = "Wyrównaj etykietę w ramce i naciśnij Skanuj",
            color = Color.White,
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 48.dp)
        )

        // capture button
        FloatingActionButton(
            onClick = {
                if (!isProcessing) onCapture()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            containerColor = if (isProcessing) Color.Gray else MaterialTheme.colorScheme.primary
        ) {
            if (isProcessing) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Icon(imageVector = Icons.Default.CameraAlt, contentDescription = "Capture")
            }
        }
    }
}