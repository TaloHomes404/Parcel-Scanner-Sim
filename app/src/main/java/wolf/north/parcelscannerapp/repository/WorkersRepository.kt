package wolf.north.parcelscannerapp.repository

import wolf.north.parcelscannerapp.mvvm.model.User

class WorkersRepository {

    //Authorized cards map for testing purposes and MVP
    //TODO: Later in app add API/Database data flow

    private val authorizedCards = mapOf(
        "04:A2:B3:C4:D5" to User("1", "Jan", "Kowalski"),
        "1A:2B:3C:4D:5E" to User("2", "Anna", "Nowak"),
        "37:3T:8D:C9:F3" to User("2", "Robert", "Testowy")
    )


    //Sorting method for card verification
    fun findUserByCardUid( cardUid: String): User? {
        //Value with formatted clean ID number from NFC Card
        val formatedUid = cardUid.replace(":", "").uppercase()
        return authorizedCards.entries.find {
            it.key.replace(":", "").uppercase() == formatedUid
        }?.value
    }
}