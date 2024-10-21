package room

import androidx.room.Database
import androidx.room.RoomDatabase
import database.Book
import room.dao.BookDao

@Database(entities = [Book::class], version = 1)
abstract class AppDatabase : RoomDatabase()  {
    abstract fun bookDao(): BookDao
}