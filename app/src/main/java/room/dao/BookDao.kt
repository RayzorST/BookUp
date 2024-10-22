package room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import database.Book
import kotlinx.coroutines.flow.Flow


@Dao
interface BookDao {
    @Query("SELECT * FROM book")
    fun getAll(): Flow<List<Book>>

    @Query("SELECT * FROM book WHERE id IN (:bookIds)")
    fun loadAllByIds(bookIds: IntArray): LiveData<List<Book>>

    @Query("SELECT * FROM book WHERE title LIKE :title")
    fun findByTitle(title: String): Book

    @Insert
    fun insert(vararg books: Book)

    @Delete
    fun delete(book: Book)
}
