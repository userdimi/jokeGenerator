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

    suspend fun getRandomJoke(): RandomJoke? {
        fetchNewRandomJoke()
        return jokeGeneratorDao.getLastSavedRandomJoke()
    }

    suspend fun fetchNewRandomJoke() {
        if (jokeGeneratorService.getRandomJoke().isSuccessful) {
            jokeGeneratorService.getRandomJoke().body()?.let {
                it.timeStamp = System.currentTimeMillis().toString()
                jokeGeneratorDao.saveRandomJoke(it)
            }
        }
    }
}