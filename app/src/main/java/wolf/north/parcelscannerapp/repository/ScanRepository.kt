package wolf.north.parcelscannerapp.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okio.IOException
import wolf.north.parcelscannerapp.api.RetrofitInstance
import wolf.north.parcelscannerapp.mvvm.model.files.Form
import wolf.north.parcelscannerapp.mvvm.model.files.Package


//Singleton - Scan Repository changed for API Calls with Retrofit
//Error message now handled in vm
object ScanRepository {

    //Instance of api
    private val api = RetrofitInstance.api

    //local state for caching in app
    private val _packages = MutableStateFlow<List<Package>>(emptyList())
    val packages: StateFlow<List<Package>> = _packages.asStateFlow()

    private val _forms = MutableStateFlow<List<Form>>(emptyList())
    val forms: StateFlow<List<Form>> = _forms.asStateFlow()

    //vals for handling error messages from api calls exceptions (to user across UI)
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    //Get all data suspender function 1 - GET
    suspend fun fetchAllData(){
        try {
            //Get all packages from api
            val packageResponse = api.getPackages()
            if (packageResponse.isSuccessful && packageResponse.body() != null) {
                _packages.value = packageResponse.body()!!
            } else {
                _errorMessage.value = "Wystąpił błąd w pobieraniu paczek: ${packageResponse.code()}"
            }

            //Get all forms from api
            val formsResponse = api.getForms()
            if (formsResponse.isSuccessful && formsResponse.body() != null) {
                _forms.value = formsResponse.body()!!
            } else {
                _errorMessage.value = "Wystąpił błąd w pobieraniu formularzy: ${formsResponse.code()}"
            }


        } catch (e: IOException) {
            //Network issue exception
            _errorMessage.value = "Błąd z połączeniem z internetem"
        } catch (e: Exception) {
            _errorMessage.value = "Wystąpił nieoczekiwany błąd: ${e.message}"
        }
    }

    suspend fun addPackage(packageData: Package): Boolean {
        return try {
            val response = api.savePackage(packageData)
            if(response.isSuccessful) {
                //Cache list in our UI, add new package to refresh UI on call
                _packages.value = listOf(packageData) + _packages.value
                true
            } else {
                _errorMessage.value = "Nie udało się zapisać paczki"
                false
            }
        } catch (e: Exception) {
            _errorMessage.value = "Błąd sieci przy zapisie paczki"
            false
        }
    }

    suspend fun addForm(form: Form): Boolean {
        return try {
            val response = api.saveForm(form)
            if(response.isSuccessful) {
                //Cache list in our UI, add new package to refresh UI on call
                _forms.value = listOf(form) + _forms.value
                true
            } else {
                _errorMessage.value = "Nie udało się zapisać formularza"
                false
            }
        } catch (e: Exception) {
            _errorMessage.value = "Błąd sieci przy zapisie formularza"
            false
        }
    }

    fun clearError(){
        _errorMessage.value = null
    }

}
