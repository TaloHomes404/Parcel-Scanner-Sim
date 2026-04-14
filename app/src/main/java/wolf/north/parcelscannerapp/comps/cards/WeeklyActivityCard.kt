package wolf.north.parcelscannerapp.comps.cards

import androidx.compose.foundation.layout.Arrangement
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
import wolf.north.parcelscannerapp.mvvm.view.Profile.BarChartItem
import wolf.north.parcelscannerapp.mvvm.view.Profile.DayActivity



@Composable
fun WeeklyActivityCard(
    modifier: Modifier = Modifier
) {
    val weekData = listOf(
        DayActivity("P", 80f),
        DayActivity("W", 60f),
        DayActivity("Ś", 90f),
        DayActivity("C", 45f),
        DayActivity("P", 70f),
        DayActivity("S", 30f),
        DayActivity("N", 10f)
    )

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
                weekData.forEach { day ->
                    BarChartItem(
                        label = day.dayName,
                        value = day.value
                    )
                }
            }
        }
    }
}
