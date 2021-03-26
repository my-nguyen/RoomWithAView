package com.nguyen.roomwithaview

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// DAOs must either be interfaces or abstract classes.
// @Dao annotation identifies it as a DAO class for Room
@Dao
interface WordDao {

    // Query that returns a list of words sorted in ascending order.
    @Query("SELECT * FROM word_table ORDER BY word ASC")
    // fun getAlphabetizedWords(): List<Word>
    // To observe data changes we will use Flow from kotlinx-coroutines
    fun getAlphabetizedWords(): Flow<List<Word>>

    // @Insert annotation is a special DAO method annotation where you don't have to provide any SQL!
    // There are also @Delete and @Update annotations for deleting and updating rows
    // onConflict = OnConflictStrategy.IGNORE: The selected onConflict strategy ignores a new word
    // if it's exactly the same as one already in the list.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    // There is no convenience annotation for deleting multiple entities, so it's annotated with
    // the generic @Query
    // @Query requires that you provide a SQL query as a string parameter to the annotation,
    // allowing for complex read queries and other operations
    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}