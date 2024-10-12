package database

import java.io.Serializable

@kotlinx.serialization.Serializable
data class Book (
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
) : Serializable