package database

@kotlinx.serialization.Serializable
data class Book (
    val id: Int = 0,
    val title: String,
    val description: String,
)