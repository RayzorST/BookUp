package room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import database.Page
import kotlinx.coroutines.flow.Flow

@Dao
interface PageDao {
    @Query("SELECT * FROM page")
    fun getAll(): Flow<List<Page>>

    @Query("SELECT * FROM page WHERE book = (:book)")
    fun loadAllByBook(book: Int): Flow<List<Page>>

    @Query("SELECT * FROM page WHERE id = (:id)")
    fun getPage(id: Int): Flow<Page>

    @Insert
    fun insert(vararg pages: Page)

    @Delete
    fun delete(page: Page)
}