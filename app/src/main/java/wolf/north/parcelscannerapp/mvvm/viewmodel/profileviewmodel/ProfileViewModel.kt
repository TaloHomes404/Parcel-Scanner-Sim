package wolf.north.parcelscannerapp.mvvm.viewmodel.profileviewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import wolf.north.parcelscannerapp.mvvm.model.worker.WorkerSession
import wolf.north.parcelscannerapp.repository.UserSessionRepository
import wolf.north.parcelscannerapp.repository.WorkersRepository


class ProfileViewModel : ViewModel() {

    private val repository = WorkersRepository()

    // UI State vals
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    //Repository singleton instance in profile view model to control user/session flow
    val currentUser = UserSessionRepository.currentUser
    val currentSession = UserSessionRepository.currentSession



    //Main method for scanning detected NFC Card
    fun onNFCCardDetected(cardUid: String){

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        val user = repository.findUserByCardUid(cardUid)

        if (user != null ){
            //Login with user NFC Card successfully passed, creating session
            //Set current user in whole app object to get values in every screen
            UserSessionRepository.login(user)

            val newSession = WorkerSession(
                sessionID = System.currentTimeMillis().toString(),
                userID = user.id,
                startTime = System.currentTimeMillis()
            )

            _uiState.value = ProfileUiState(
                isLoggedIn = true,
                currentUser = user,
                currentSession = newSession
            )
        } else {
            //Unrecognized card UID, aborted session with error message
            _uiState.value = ProfileUiState(
                isLoggedIn = false,
                errorMessage = "Unrecognized card UID"
            )
        }
    }


    //Logout card for button functionality
    //TODO: Later add analysis and charts sending for logout method

    fun logout(){
        UserSessionRepository.logout()
        _uiState.value = ProfileUiState() // Reset of local state
    }
}