package wolf.north.parcelscannerapp.mvvm.model.worker

data class WorkerSession(
    val sessionID: String,
    val userID: String,
    val startTime: Long,
    val endTime: Long? = null,
    val scannedPackages: Int = 0,
    val scannedForms: Int = 0
)

