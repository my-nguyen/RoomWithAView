package com.nguyen.roomwithaview

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// The database class for Room must be abstract and extend RoomDatabase
// annotate the class with @Database and use the annotation parameters to declare the entities that
// belong in the database and set the version number. Each entity corresponds to a table that will
// be created in the database
@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
public abstract class WordRoomDatabase : RoomDatabase() {

    // The database exposes DAOs through an abstract "getter" method for each @Dao
    abstract fun wordDao(): WordDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        // To launch a coroutine we need a CoroutineScope
        fun getDatabase(context: Context, scope: CoroutineScope): WordRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, WordRoomDatabase::class.java, "word_database")
                    .addCallback(Callback(scope))
                    .build()

                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class Callback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.wordDao())
                }
            }
        }

        suspend fun populateDatabase(wordDao: WordDao) {
            // Delete all content here.
            wordDao.deleteAll()

            // Add sample words.
            var word = Word("Hello")
            wordDao.insert(word)
            word = Word("World!")
            wordDao.insert(word)

            // TODO: Add your own words!
        }
    }
}