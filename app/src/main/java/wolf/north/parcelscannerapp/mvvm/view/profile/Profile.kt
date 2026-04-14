package wolf.north.parcelscannerapp.mvvm.view.profile

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import wolf.north.parcelscannerapp.comps.cards.EmployeeProfileCard
import wolf.north.parcelscannerapp.comps.cards.StatsCard
import wolf.north.parcelscannerapp.comps.cards.WeeklyActivityCard
import wolf.north.parcelscannerapp.comps.items.ProfileMenuItem
import wolf.north.parcelscannerapp.mvvm.viewmodel.profileviewmodel.ProfileUiState
import wolf.north.parcelscannerapp.utils.formatTime


@Composable
fun ProfileScreen(
    uiState: ProfileUiState = ProfileUiState(),
    onEditClick: () -> Unit = {},
    onMenuClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme

    //placeholders vals for cards
    val user = uiState.currentUser
    val session = uiState.currentSession

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item { Spacer(modifier = Modifier.height(8.dp)) }

        // Worker info card
        item {
            if (user != null) {
                EmployeeProfileCard(
                    initials = "${user.name.firstOrNull() ?: ""}${user.lastName.firstOrNull() ?: ""}",  // Simple initials getter
                    name = "${user.name} ${user.lastName}",
                    employeeId = user.id,
                    email = "${user.name.lowercase()}.${user.lastName.lowercase()}@firma.pl",
                    position = user.position,
                    startTime = formatTime(session?.startTime),
                    onEditClick = onEditClick
                )
            } else {
                EmployeeProfileCard(
                    initials = "AB",
                    name = "Imie Nazwisko",
                    employeeId = "ID:",
                    email = "",
                    position = "",
                    startTime = "--:--",
                    onEditClick = onEditClick
                )
            }
        }

        //Pie chart card
        item {
            StatsCard(
                scanned = (session?.scannedPackages ?: 0) + ((session?.scannedForms ?: 0)),
                delivered = 1,
                inTransit = 0,
                onCardClick = {  }
            )
        }

        // Weekly activity worker card
        item {
            WeeklyActivityCard()
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {

                    // Worker Data Change
                    ProfileMenuItem(
                        icon = Icons.Default.Person,
                        iconBackground = colorScheme.primary.copy(alpha = 0.12f),
                        iconTint = colorScheme.primary,
                        text = "Dane pracownika",
                        onClick = {
                            // TODO: Logika podglądu/edycji danych
                        }
                    )

                    // Scanner Options
                    ProfileMenuItem(
                        icon = Icons.Default.QrCodeScanner,
                        iconBackground = colorScheme.tertiary.copy(alpha = 0.12f),
                        iconTint = colorScheme.tertiary,
                        text = "Ustawienia skanera",
                        onClick = {
                            // TODO: Nawigacja do ustawień skanera
                        }
                    )

                    // Report problem with app
                    ProfileMenuItem(
                        icon = Icons.Default.BugReport,
                        iconBackground = colorScheme.errorContainer.copy(alpha = 0.5f),
                        iconTint = colorScheme.error,
                        text = "Zgłoś problem",
                        onClick = {
                            // TODO: Otwórz formularz zgłoszeniowy lub maila
                        }
                    )

                    // Log Out
                    ProfileMenuItem(
                        icon = Icons.AutoMirrored.Filled.ExitToApp,
                        iconBackground = colorScheme.secondary.copy(alpha = 0.12f),
                        iconTint = colorScheme.secondary,
                        text = "Wyloguj",
                        onClick = {
                            // TODO: Logika wylogowania
                        },
                        showDivider = false
                    )
                }
            }
        }
        item { Spacer(modifier = Modifier.height(8.dp)) }
    }
}

@Composable
fun RowScope.BarChartItem(
    label: String,
    value: Float,
    modifier: Modifier = Modifier
) {
    val maxBarHeight = 100.dp
    val barHeight = maxBarHeight * (value / 100f)

    val barGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF4DD0E1),
            Color(0xFF1A237E)
        )
    )

    Column(
        modifier = modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier
                .width(35.dp)
                .height(barHeight)
                .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                .background(barGradient)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// Helper class for row in activity chart
data class DayActivity(
    val dayName: String,
    val value: Float
)




@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}