package room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import database.Book
import database.Page
import room.dao.BookDao
import room.dao.PageDao


@Database(entities = [Book::class, Page::class], version = 3)
abstract class AppDatabase : RoomDatabase()  {
    abstract fun bookDao(): BookDao
    abstract fun pageDao(): PageDao
}

val MIGRATION_1_2: Migration = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE 'page'(id INTEGER PRIMARY KEY NOT NULL)")
        database.execSQL("ALTER TABLE 'page' ADD COLUMN 'page' INTEGER NOT NULL")
        database.execSQL("ALTER TABLE 'page' ADD COLUMN 'text' TEXT NOT NULL")
        database.execSQL("ALTER TABLE 'page' ADD COLUMN 'book' INTEGER NOT NULL")
    }
}