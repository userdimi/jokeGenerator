package de.pottcode.jokegenerator.repository

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

    suspend fun getRandomJokeFromNetwork(): RandomJoke? {
        fetchNewRandomJoke()
        return jokeGeneratorDao.getLastSavedRandomJoke()
    }

    private suspend fun fetchNewRandomJoke() {
        val randomJokeResponse = jokeGeneratorService.getRandomJoke()
        if (randomJokeResponse.isSuccessful) {
            randomJokeResponse.body()?.let {
                it.timeStamp = System.currentTimeMillis().toString()
                jokeGeneratorDao.saveRandomJoke(it)
            }
        } else {
            throw RandomJokeFetchError("Error by fetching a random joke", Throwable())
        }
    }

    suspend fun getRandomJokeFromDatabase(): RandomJoke {
        return jokeGeneratorDao.getLastSavedRandomJoke()
    }

    class RandomJokeFetchError(message: String, cause: Throwable?) : Throwable(message, cause)
}