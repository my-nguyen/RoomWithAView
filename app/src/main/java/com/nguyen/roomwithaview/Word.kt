package com.nguyen.roomwithaview

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Each @Entity class represents a SQLite table. specify the name of the table if you want it to be
// different from the name of the class. This names the table "word_table".
// @PrimaryKey Every entity needs a primary key
// @ColumnInfo Specifies the name of the column in the table if you want it to be different from
// the name of the member variable
// Every property that's stored in the database needs to have public visibility
@Entity(tableName = "word_table")
class Word(@PrimaryKey @ColumnInfo(name = "word") val word: String)
