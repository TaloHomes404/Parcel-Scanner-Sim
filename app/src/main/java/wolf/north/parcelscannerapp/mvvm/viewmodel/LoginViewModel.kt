package wolf.north.parcelscannerapp.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Mocked Uid's of cards
    private val authorizedUids = listOf("04A2B3C4D5", "1A2B3C4D5E")

    fun onNfcCardDetected(cardUid: String) {
        _isLoading.value = true
        _errorMessage.value = null

        //TODO: Add viewmodelscope launch for fake api/ reading data profile

        val cleanUid = cardUid.replace(":", "").uppercase()

        //Control of login status success / fail
        if (authorizedUids.contains(cleanUid)) {
            _loginSuccess.value = true
        } else {
            _isLoading.value = false
            _errorMessage.value = "BŁĄD: Nieznana karta lub brak dostępu!\nSpróbuj ponownie."
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}