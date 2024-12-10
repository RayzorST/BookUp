package room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import database.Book
import database.Page
import room.dao.BookDao
import room.dao.PageDao


@Database(entities = [Book::class, Page::class], version = 5)
abstract class AppDatabase : RoomDatabase()  {
    abstract fun bookDao(): BookDao
    abstract fun pageDao(): PageDao
}

val MIGRATION_1_2: Migration = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE 'book' ADD COLUMN 'image' TEXT NOT NULL default 'abc'")
    }
}