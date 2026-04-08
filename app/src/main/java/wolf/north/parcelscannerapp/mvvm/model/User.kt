package wolf.north.parcelscannerapp.mvvm.model

data class User (
    val id: String,
    val name: String,
    val lastName: String,
    val position: String = "Sort Section Worker"
)
