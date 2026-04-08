package wolf.north.parcelscannerapp.mvvm.viewmodel.profileviewmodel

import wolf.north.parcelscannerapp.mvvm.model.User
import wolf.north.parcelscannerapp.mvvm.model.worker.WorkerSession

// UI state profile data class
data class ProfileUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val currentUser: User? = null,
    val currentSession: WorkerSession? = null,
    val errorMessage: String? = null
)