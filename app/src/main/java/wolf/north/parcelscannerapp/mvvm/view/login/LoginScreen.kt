package wolf.north.parcelscannerapp.mvvm.view.login

import android.content.Intent
import android.nfc.NfcAdapter
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contactless
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import wolf.north.parcelscannerapp.mvvm.viewmodel.LoginViewModel

// Simple helper for drawing lanes with spaces inbetween for "card" in login screen
fun Modifier.dashedBorder(color: Color, strokeWidth: Float, dashLength: Float, gapLength: Float) = this.then(
    Modifier.drawBehind {
        val stroke = Stroke(
            width = strokeWidth,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, gapLength), 0f)
        )
        drawRect(
            color = color,
            style = stroke
        )
    }
)

@Composable
fun LoginScreen(
    nfcIntent: Intent?,
    viewModel: LoginViewModel
) {
    val context = LocalContext.current

    // Coroutine for receiving NFC Intent from MainActivity
    LaunchedEffect(nfcIntent) {
        nfcIntent?.let {
            if (NfcAdapter.ACTION_TAG_DISCOVERED == it.action) {
                val idArray = it.getByteArrayExtra(NfcAdapter.EXTRA_ID)
                idArray?.let { bytes ->
                    val cardUid = bytes.joinToString(":") { b -> String.format("%02X", b) }
                    viewModel.onNfcCardDetected(cardUid)
                }
            }
        }
    }

    // ViewModel States for LoginScreen flow
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Animation of "blinking" card on login screen
    val infiniteTransition = rememberInfiniteTransition(label = "borderAnimation")
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color.Gray.copy(alpha = 0.5f),
        targetValue = MaterialTheme.colorScheme.primary,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "borderColor"
    )

    // Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            // Text on top of screen
            Text(
                text = "Dzień dobry!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Gotowy na zmianę?",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // NFC Scan Card Content
            Box(
                modifier = Modifier
                    .size(width = 280.dp, height = 160.dp)
                    .dashedBorder(
                        color = animatedColor, // Blinking animation added
                        strokeWidth = 4f,
                        dashLength = 20f,
                        gapLength = 15f
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Contactless,
                        contentDescription = "NFC Icon",
                        modifier = Modifier.size(48.dp),
                        tint = animatedColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Przyłóż kartę pracownika\ndo ekranu",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Flow of login try / Verification of card Uid's, Error Receiver
            when {
                isLoading -> {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Weryfikacja...", color = MaterialTheme.colorScheme.primary)
                }
                errorMessage != null -> {
                    // Error message section
                    Text(
                        text = errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable { viewModel.clearError() } // Clickable for cleaning error message / retry flow
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Dotknij aby spróbować ponownie",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}