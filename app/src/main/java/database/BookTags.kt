package database

import java.io.Serializable

@kotlinx.serialization.Serializable
data class BookTags(
    val id: Int = 0,
    val book_id: Int = 0,
    val tag_id: Int = 0,
    ): Serializable
