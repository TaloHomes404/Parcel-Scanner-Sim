package wolf.north.parcelscannerapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import wolf.north.parcelscannerapp.mvvm.view.splashscreen.SplashScreen
import wolf.north.parcelscannerapp.navigation.AppNavigation
import wolf.north.parcelscannerapp.ui.theme.ParcelScannerSimTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParcelScannerSimTheme {
                ParcelScannerEntryPoint()
            }
        }
    }
}


@SuppressLint("ContextCastToActivity")
@Composable
fun ParcelScannerEntryPoint() {
    // Switch val to show splash/loginscreen
    var showSplash by remember { mutableStateOf(true) }

    // Intent of NFC value
    var nfcIntent by remember { mutableStateOf<Intent?>(null) }

    val activity = LocalContext.current as Activity
    LaunchedEffect(Unit) {
        nfcIntent = activity.intent
    }



    // Delay for splash display coroutine
    LaunchedEffect(Unit) {
        delay(2000)
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
    } else {
        //After Splash Screen display navcontroller to navigate in applicaton (to don't set resources during splashscreen)
        val navController = rememberNavController()
        AppNavigation(navController = navController, nfcIntent = nfcIntent)
    }
}


//Preview home screen
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ParcelScannerSimTheme {
        // maybe don't create nav instance in preview next time XD (40 min saved)
    }
}