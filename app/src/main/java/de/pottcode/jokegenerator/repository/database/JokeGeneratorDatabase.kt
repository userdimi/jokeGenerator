package de.pottcode.jokegenerator.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import de.pottcode.jokegenerator.repository.model.RandomJoke

/**
 * (c) Dimitri Simon on 12.07.20
 */
@Database(entities = [RandomJoke::class], version = 1, exportSchema = true)
abstract class JokeGeneratorDatabase : RoomDatabase() {
    abstract fun randomJokeDao(): JokeGeneratorDao
}

