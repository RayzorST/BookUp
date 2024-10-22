package room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import database.Book
import room.dao.BookDao


@Database(entities = [Book::class], version = 2)
abstract class AppDatabase : RoomDatabase()  {
    abstract fun bookDao(): BookDao
}

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {

    }
}