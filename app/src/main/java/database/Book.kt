package database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import room.repository.Book
import java.io.Serializable

@Entity(tableName = "book")
@kotlinx.serialization.Serializable
data class Book (
    @PrimaryKey var id: Int = 0,
    @ColumnInfo var title: String = "",
    @ColumnInfo var description: String = "",
    @Ignore var isFavorite: Boolean = true,
) : Serializable