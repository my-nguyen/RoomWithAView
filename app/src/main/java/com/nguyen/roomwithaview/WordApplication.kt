package com.nguyen.roomwithaview

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordApplication : Application() {
    // Populating the database isn't related to a UI lifecycle, therefore, we should not use a
    // CoroutineScope like viewModelScope. Rather, this is related to the app's lifecycle. So, we'll
    // update the WordsApplication to contain an applicationScope. We'll then pass that to the
    // WordRoomDatabase.getDatabase
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { WordRepository(database.wordDao()) }
}