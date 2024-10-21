package database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
@kotlinx.serialization.Serializable
data class Book (
    @PrimaryKey val id: Int = 0,
    @ColumnInfo val title: String = "",
    @ColumnInfo val description: String = "",
) : Serializable