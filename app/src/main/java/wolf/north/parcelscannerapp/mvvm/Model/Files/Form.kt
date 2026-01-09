package wolf.north.parcelscannerapp.mvvm.Model.Files

//
//Model class for scanner form
//
data class Form(
    val firstName: String? = null,
    val lastName: String? = null,
    val date: String? = null,
    val id: String? = null,
    val address: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val additionalNotes: String? = null
) {
    //
    //Methods helpful with scanner form
    //

    fun isComplete(): Boolean{
        return !firstName.isNullOrBlank() &&
                !lastName.isNullOrBlank() &&
                !date.isNullOrBlank()
    }

    fun getFullName(): String {
        return listOfNotNull(firstName, lastName)
            .filter { it.isNotBlank() }
            .joinToString(" ")
            .ifBlank { "Brak informacji" }
    }

    //Return count of fields entered
    //Validation all fields : entered
    fun getFilledFieldsCount() : Int {
        return listOfNotNull(
            firstName,lastName, date, id, address,  phoneNumber, email, additionalNotes).count() { it.isNotBlank() }
    }
}
