package wolf.north.parcelscannerapp.mvvm.model.login

import wolf.north.parcelscannerapp.mvvm.model.User
import wolf.north.parcelscannerapp.mvvm.model.worker.WorkerSession

data class LoginRequest(
    val cardUid: String
)

// To dostajemy w odpowiedzi
data class LoginResponse(
    val user: User,
    val session: WorkerSession,
    val token: String? = null
)