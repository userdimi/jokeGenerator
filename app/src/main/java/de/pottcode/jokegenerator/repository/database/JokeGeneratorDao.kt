package de.pottcode.jokegenerator.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.pottcode.jokegenerator.repository.model.RandomJoke

/**
 * (c) Dimitri Simon on 12.07.20
 */

@Dao
interface JokeGeneratorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRandomJoke(randomJoke: RandomJoke)

    @Query("SELECT * FROM randomjoke ORDER BY timeStamp DESC LIMIT 1")
    fun getLastSavedRandomJoke(): LiveData<RandomJoke?>

}