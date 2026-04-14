package wolf.north.parcelscannerapp.utils

fun formatTime(timestamp: Long?): String {
    if (timestamp == null) return "--:--"
    val sdf = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
    return sdf.format(java.util.Date(timestamp))
}