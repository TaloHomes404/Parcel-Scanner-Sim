package wolf.north.parcelscannerapp.comps.scanners

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun PackageScannerOverlay(
    modifier: Modifier = Modifier,
    isProcessing: Boolean,
    onCapture: () -> Unit,
    onClose: () -> Unit = {},
    isFlashOn: Boolean = false,
    onToggleFlash: () -> Unit = {}
) {
    val colorScheme = MaterialTheme.colorScheme
    val primaryColor = colorScheme.primary
    val onPrimaryColor = colorScheme.onPrimary

    val scanFrameWidth = 300.dp
    val scanFrameHeight = 200.dp
    val cornerLength = 32.dp
    val cornerThickness = 4.dp
    val cornerRadius = 16.dp

    Box(modifier = modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Skanowanie paczki",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(
                text = "Umieść etykietę w obszarze skanowania",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f)
            )
        }

        Box(
            modifier = Modifier.align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {

            //Canvas for lane-corners for scan field in package scanner
            Canvas(
                modifier = Modifier.size(scanFrameWidth, scanFrameHeight)
            ) {
                val cornerPx = cornerLength.toPx()
                val thicknessPx = cornerThickness.toPx()
                val radiusPx = cornerRadius.toPx()

                val stroke = Stroke(
                    width = thicknessPx,
                    pathEffect = PathEffect.cornerPathEffect(radiusPx)
                )

                drawLine(primaryColor, Offset(0f, cornerPx), Offset(0f, 0f), strokeWidth = thicknessPx, cap = androidx.compose.ui.graphics.StrokeCap.Round)
                drawLine(primaryColor, Offset(0f, 0f), Offset(cornerPx, 0f), strokeWidth = thicknessPx, cap = androidx.compose.ui.graphics.StrokeCap.Round)

                drawLine(primaryColor, Offset(size.width - cornerPx, 0f), Offset(size.width, 0f), strokeWidth = thicknessPx, cap = androidx.compose.ui.graphics.StrokeCap.Round)
                drawLine(primaryColor, Offset(size.width, 0f), Offset(size.width, cornerPx), strokeWidth = thicknessPx, cap = androidx.compose.ui.graphics.StrokeCap.Round)

                drawLine(primaryColor, Offset(0f, size.height - cornerPx), Offset(0f, size.height), strokeWidth = thicknessPx, cap = androidx.compose.ui.graphics.StrokeCap.Round)
                drawLine(primaryColor, Offset(0f, size.height), Offset(cornerPx, size.height), strokeWidth = thicknessPx, cap = androidx.compose.ui.graphics.StrokeCap.Round)

                drawLine(primaryColor, Offset(size.width - cornerPx, size.height), Offset(size.width, size.height), strokeWidth = thicknessPx, cap = androidx.compose.ui.graphics.StrokeCap.Round)
                drawLine(primaryColor, Offset(size.width, size.height), Offset(size.width, size.height - cornerPx), strokeWidth = thicknessPx, cap = androidx.compose.ui.graphics.StrokeCap.Round)
            }
        }

        // Bottom section with buttons of package scanner
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 64.dp, start = 32.dp, end = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Cancel button
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
                    .size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Cancel,
                    contentDescription = "Anuluj",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Scan button
            FloatingActionButton(
                onClick = { if (!isProcessing) onCapture() },
                containerColor = if (isProcessing) colorScheme.onSurface.copy(alpha = 0.5f) else primaryColor,
                modifier = Modifier.size(72.dp)
            ) {
                if (isProcessing) {
                    androidx.compose.material3.CircularProgressIndicator(
                        modifier = Modifier.size(28.dp),
                        color = onPrimaryColor,
                        strokeWidth = 3.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Skanuj",
                        tint = onPrimaryColor,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Flashlight
            IconButton(
                onClick = onToggleFlash,
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
                    .size(50.dp)
            ) {
                Icon(
                    imageVector = if (isFlashOn) Icons.Default.FlashOn else Icons.Default.FlashOff,
                    contentDescription = "Lampa",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}



@Composable
fun FormScannerOverlay(
    modifier: Modifier = Modifier,
    isProcessing: Boolean,
    onCapture: () -> Unit,
    onClose: () -> Unit = {},
    isFlashOn: Boolean = false,
    onGalleryToggle: () -> Unit = {},
    onToggleFlash: () -> Unit = {}
) {
    val colorScheme = MaterialTheme.colorScheme
    val primaryColor = colorScheme.primary
    val onPrimaryColor = colorScheme.onPrimary

    val scanFrameWidth = 300.dp
    val scanFrameHeight = 400.dp
    val cornerLength = 32.dp
    val cornerThickness = 4.dp
    val cornerRadius = 16.dp

    Box(modifier = modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Skanowanie formularza",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(
                text = "Umieść formularz w polu skanowania",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f)
            )
        }

        Box(
            modifier = Modifier.align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {

            //Canvas for lane-corners for scan field in form scanner
            Canvas(
                modifier = Modifier.size(scanFrameWidth, scanFrameHeight)
            ) {
                val cornerPx = cornerLength.toPx()
                val thicknessPx = cornerThickness.toPx()
                val radiusPx = cornerRadius.toPx()

                val stroke = Stroke(
                    width = thicknessPx,
                    pathEffect = PathEffect.cornerPathEffect(radiusPx)
                )

                drawLine(primaryColor, Offset(0f, cornerPx), Offset(0f, 0f), strokeWidth = thicknessPx, cap = androidx.compose.ui.graphics.StrokeCap.Round)
                drawLine(primaryColor, Offset(0f, 0f), Offset(cornerPx, 0f), strokeWidth = thicknessPx, cap = androidx.compose.ui.graphics.StrokeCap.Round)

                drawLine(primaryColor, Offset(size.width - cornerPx, 0f), Offset(size.width, 0f), strokeWidth = thicknessPx, cap = androidx.compose.ui.graphics.StrokeCap.Round)
                drawLine(primaryColor, Offset(size.width, 0f), Offset(size.width, cornerPx), strokeWidth = thicknessPx, cap = androidx.compose.ui.graphics.StrokeCap.Round)

                drawLine(primaryColor, Offset(0f, size.height - cornerPx), Offset(0f, size.height), strokeWidth = thicknessPx, cap = androidx.compose.ui.graphics.StrokeCap.Round)
                drawLine(primaryColor, Offset(0f, size.height), Offset(cornerPx, size.height), strokeWidth = thicknessPx, cap = androidx.compose.ui.graphics.StrokeCap.Round)

                drawLine(primaryColor, Offset(size.width - cornerPx, size.height), Offset(size.width, size.height), strokeWidth = thicknessPx, cap = androidx.compose.ui.graphics.StrokeCap.Round)
                drawLine(primaryColor, Offset(size.width, size.height), Offset(size.width, size.height - cornerPx), strokeWidth = thicknessPx, cap = androidx.compose.ui.graphics.StrokeCap.Round)
            }
        }

        // Bottom section with buttons of form scanner
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 64.dp, start = 32.dp, end = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Cancel button
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
                    .size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Cancel,
                    contentDescription = "Anuluj",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Scan button
            FloatingActionButton(
                onClick = { if (!isProcessing) onCapture() },
                containerColor = if (isProcessing) colorScheme.onSurface.copy(alpha = 0.5f) else primaryColor,
                modifier = Modifier.size(72.dp)
            ) {
                if (isProcessing) {
                    androidx.compose.material3.CircularProgressIndicator(
                        modifier = Modifier.size(28.dp),
                        color = onPrimaryColor,
                        strokeWidth = 3.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Skanuj",
                        tint = onPrimaryColor,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Flashlight
            IconButton(
                onClick = onToggleFlash,
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
                    .size(50.dp)
            ) {
                Icon(
                    imageVector = if (isFlashOn) Icons.Default.FlashOn else Icons.Default.FlashOff,
                    contentDescription = "Lampa",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Gallery
            IconButton(
                onClick = onToggleFlash,
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
                    .size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Photo,
                    contentDescription = "Galeria",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

        }
    }
}

