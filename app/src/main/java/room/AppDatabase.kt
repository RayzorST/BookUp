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

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
    }
}

val MIGRATION_2_3: Migration = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
    }
}

val MIGRATION_3_4: Migration = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
    }
}

val MIGRATION_4_5: Migration = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE 'book' ADD COLUMN 'image' TEXT NOT NULL default 'abc'")
    }
}