package wolf.north.parcelscannerapp.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import wolf.north.parcelscannerapp.mvvm.Model.files.Form
import wolf.north.parcelscannerapp.mvvm.Model.files.Package


//Singleton - single instance for whole app (for MVP - testing purposes
object ScanRepository {

    //List of packages
    private val _packages = MutableStateFlow<List<Package>>(emptyList())
    val packages: StateFlow<List<Package>> = _packages.asStateFlow()

    //List of forms
    private val _forms = MutableStateFlow<List<Form>>(emptyList())
    val forms: StateFlow<List<Form>> = _forms.asStateFlow()

    //**
    //Methods
    //**

    // Add - Package
    fun addPackage(packageData: Package){
        _packages.value = listOf(packageData) + _packages.value
    }

    // Delete - Package
    fun deletePackage(packageData: Package){
        _packages.value = _packages.value.filter { it != packageData }
    }

    // Add - Form
    fun addForm(form: Form){
        _forms.value = listOf(form) + _forms.value
    }

    // Delete - Form
    fun deleteForm(form: Form){
        _forms.value = _forms.value.filter { it != form }
    }

    // Delete - EVERYTHING, EVEN APP
    fun clearAll() {
        _packages.value = emptyList()
        _forms.value = emptyList()
    }

}