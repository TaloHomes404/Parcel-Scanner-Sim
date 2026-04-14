package wolf.north.parcelscannerapp.comps.charts

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun StatsPieChart(
    scannedPackages: Int,
    scannedForms: Int,
    inTransit: Int,
    modifier: Modifier = Modifier
) {
    val total = (scannedPackages + scannedForms + inTransit).toFloat()

    // Section of pie chart slices color definition
    val scannedColor = MaterialTheme.colorScheme.primary
    val deliveredColor = Color(0xFF4CAF50)
    val inTransitColor = Color(0xFFFFC107)
    val trackColor = MaterialTheme.colorScheme.surfaceVariant

    Canvas(modifier = modifier) {
        val strokeWidth = size.minDimension * 0.15f
        var startAngle = -90f

        // Background of pie chart
        drawArc(
            color = trackColor,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        fun drawSlice(value: Int, color: Color) {
            if (value > 0 && total > 0) {
                val sweepAngle = 360f * (value / total)
                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )
                startAngle += sweepAngle // Angle for next slice
            }
        }

        drawSlice(scannedPackages, scannedColor)
        drawSlice(scannedForms, deliveredColor)
        drawSlice(inTransit, inTransitColor)
    }
}