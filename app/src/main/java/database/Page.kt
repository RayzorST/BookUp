package database

import java.io.Serializable

@kotlinx.serialization.Serializable
data class Page(
    val id: Int = 0,
    val book: Int = 0,
    val page: Int = 0,
    val text: String = "",
) : Serializable
