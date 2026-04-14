package wolf.north.parcelscannerapp.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import wolf.north.parcelscannerapp.mvvm.model.User
import wolf.north.parcelscannerapp.mvvm.model.worker.WorkerSession

object UserSessionRepository {
    // Private backing field
    private val _currentUser = MutableStateFlow<User?>(null)
    private val _currentSession = MutableStateFlow<WorkerSession?>(null)

    // Public read-only flows
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    val currentSession: StateFlow<WorkerSession?> = _currentSession.asStateFlow()

    // Logon NFC for user/session
    fun login(user: User) {
        _currentUser.value = user
        _currentSession.value = WorkerSession(
            sessionID = System.currentTimeMillis().toString(),
            userID = user.id,
            startTime = System.currentTimeMillis()
        )
    }

    // Counter of package increaser helper method
    fun incrementPackageCount() {
        val current = _currentSession.value ?: return
        _currentSession.value = current.copy(
            scannedPackages = current.scannedPackages + 1
        )
    }

    fun incrementFormCount() {
        val current = _currentSession.value ?: return
        _currentSession.value = current.copy(
            scannedForms = current.scannedForms + 1
        )
    }

    // Logout session/user reset
    fun logout() {
        // TODO: Tu można dodać wysyłanie danych na serwer przed wyczyszczeniem
        _currentUser.value = null
        _currentSession.value = null
    }
}