package wolf.north.parcelscannerapp.mvvm.Model.files

import java.time.LocalDate

data class Package(
    val trackingNumber: String,
    val courier: Courier,
    val scanDate: String,
    val weightClass: WeightClass,
    val shipmentType: ShipmentType
)

enum class WeightClass(val displayName: String, val maxWeight: Double) {
    A("Lekka (do 5kg)", 5.0),
    B("Średnia (5-15kg)", 15.0),
    C("Ciężka (15-30kg)", 30.0)
}


//ENUM class for package type
enum class ShipmentType(val displayName: String) {
    PARCEL("Paczka"),
    ENVELOPE("Koperta"),
    DOCUMENT("Dokument")
}


//
// Enum class for local couriers codes
//
enum class Courier(val displayName: String, val prefix: List<String>) {

    INPOST("InPost", listOf("620", "630")),
    DHL("DHL", listOf("JJD", "JD")),
    DPD("DPD", listOf("05")),
    UPS("UPS", listOf("1Z")),
    FEDEX("FedEx", listOf("96", "61")),
    POCZTA_POLSKA("Poczta Polska", listOf("RR", "CP")),
    UNKNOWN("Nieznany", emptyList());


    //
    //Creating default object to recognize courier by code
    //
    companion object {

        fun fromTrackingNumber(code: String) : Courier{
            return entries.firstOrNull { courier ->
                courier.prefix.any { prefix ->
                    code.startsWith(prefix, ignoreCase = true)
                }
            } ?: UNKNOWN
        }
    }
}



