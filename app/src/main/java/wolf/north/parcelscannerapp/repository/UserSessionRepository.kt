package wolf.north.parcelscannerapp.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import wolf.north.parcelscannerapp.api.RetrofitInstance
import wolf.north.parcelscannerapp.api.RetrofitInstance.api
import wolf.north.parcelscannerapp.mvvm.model.User
import wolf.north.parcelscannerapp.mvvm.model.login.LoginRequest
import wolf.north.parcelscannerapp.mvvm.model.stats.UserStatsResponse
import wolf.north.parcelscannerapp.mvvm.model.worker.WorkerSession

object UserSessionRepository {
    // Private backing field
    private val _currentUser = MutableStateFlow<User?>(null)
    private val _currentSession = MutableStateFlow<WorkerSession?>(null)
    private val _stats = MutableStateFlow<UserStatsResponse?>(null)


    // Public read-only flows
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    val currentSession: StateFlow<WorkerSession?> = _currentSession.asStateFlow()
    val stats: StateFlow<UserStatsResponse?> = _stats.asStateFlow()


    //val for handling error messages from api calls exceptions (to user across UI)
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // Logon NFC for user/session
    fun login(user: User) {
        _currentUser.value = user
        _currentSession.value = WorkerSession(
            sessionID = System.currentTimeMillis().toString(),
            userID = user.id,
            startTime = System.currentTimeMillis()
        )
    }

    suspend fun login(cardUid: String): Boolean {
        return try {
            //Send ID of Card to API Call for auth
            val response = RetrofitInstance.api.login(LoginRequest(cardUid))

            if (response.isSuccessful && response.body() != null ) {
                //Successfully handle user logon
                val loginData = response.body()!!
                _currentUser.value = loginData.user
                _currentSession.value = loginData.session
                true
            } else {
                _errorMessage.value = "Nieznana karta lub brak dostępu"
                false
            }
        } catch (e: Exception) {
            _errorMessage.value = "Błąd sieci przy logowaniu"
            false
        }
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

    suspend fun fetchStats() {
        try {
            val response = RetrofitInstance.api.getUserStats()
            if (response.isSuccessful && response.body() != null) {
                _stats.value = response.body()
            }
        } catch (e: Exception) {
            // Obsługa błędu
        }
    }

        // Logout session/user reset
        suspend fun logout() {
            try {
                RetrofitInstance.api.logout()
            } catch (e: Exception) {
                // Ignorujemy błędy, i tak czyścimy lokalnie
            } finally {
                // Zawsze czyścimy lokalny stan
                _currentUser.value = null
                _currentSession.value = null
                _stats.value = null
            }
        }
}