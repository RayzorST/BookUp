package database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "page")
@kotlinx.serialization.Serializable
data class Page(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo val book: Int = 0,
    @ColumnInfo val page: Int = 0,
    @ColumnInfo val text: String = "",
) : Serializable
