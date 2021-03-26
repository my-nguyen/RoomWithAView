package com.nguyen.roomwithaview

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor.
// The DAO is passed into the repository constructor as opposed to the whole database. This is
// because it only needs access to the DAO, since the DAO contains all the read/write methods for
// the database. There's no need to expose the entire database to the repository
class WordRepository(private val wordDao: WordDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()

    // Room executes suspend queries off the main thread. The suspend modifier tells the compiler
    // that this needs to be called from a coroutine or another suspending function
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}