package room.repository

import android.util.Log
import database.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import room.dao.BookDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class Book(private val bookDao: BookDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun getAll() : Flow<List<Book>> {
        return bookDao.getAll()
    }

    fun addBook(book: Book) {
        coroutineScope.launch(Dispatchers.IO) {
            bookDao.insert(book)
        }
    }

    fun deleteBook(book: Book) {
        coroutineScope.launch(Dispatchers.IO) {
            bookDao.delete(book)
        }
    }
}