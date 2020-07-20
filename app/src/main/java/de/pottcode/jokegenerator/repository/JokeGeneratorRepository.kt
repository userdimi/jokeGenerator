package de.pottcode.jokegenerator.repository

import androidx.lifecycle.LiveData
import de.pottcode.jokegenerator.repository.database.JokeGeneratorDao
import de.pottcode.jokegenerator.repository.model.RandomJoke
import de.pottcode.jokegenerator.repository.network.JokeGeneratorService
import javax.inject.Inject

/**
 * (c) Dimitri Simon on 08.07.20
 */
class JokeGeneratorRepository @Inject constructor(
    private val jokeGeneratorService: JokeGeneratorService,
    private val jokeGeneratorDao: JokeGeneratorDao
) {

    val randomJoke: LiveData<RandomJoke?> = jokeGeneratorDao.getLastSavedRandomJoke()

    suspend fun fetchRandomJokeFromNetwork() {
        try {
            val randomJokeResponse = jokeGeneratorService.getRandomJoke()
            if (randomJokeResponse.isSuccessful) {
                randomJokeResponse.body()?.let {
                    it.timeStamp = System.currentTimeMillis().toString()
                    jokeGeneratorDao.saveRandomJoke(it)
                }
            }
        } catch (exception: Throwable) {
            throw RandomJokeFetchException("Error by fetching a random joke", Throwable())
        }
    }

    class RandomJokeFetchException(message: String, cause: Throwable?) : Throwable(message, cause)
}