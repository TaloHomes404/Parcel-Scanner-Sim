package wolf.north.parcelscannerapp.comps.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import wolf.north.parcelscannerapp.mvvm.view.profile.BarChartItem
import wolf.north.parcelscannerapp.mvvm.view.profile.DayActivity



@Composable
fun WeeklyActivityCard(
    modifier: Modifier = Modifier,
    activityData: List<DayActivity>
) {
    if (activityData.isEmpty()) {
        // Placeholder lub pusty stan
        Box(modifier = modifier.height(150.dp)) { Text("Brak danych") }
        return
    }

    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Aktywność tygodniowa",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                activityData.forEach { day ->
                    BarChartItem(
                        label = day.dayName,
                        value = day.value
                    )
                }
            }
        }
    }
}
