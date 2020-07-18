package de.pottcode.jokegenerator.repository.network

import de.pottcode.jokegenerator.repository.model.RandomJoke
import retrofit2.Response
import retrofit2.http.GET

/**
 * (c) Dimitri Simon on 08.07.20
 */

interface JokeGeneratorService {
    @GET("random")
    suspend fun getRandomJoke(): Response<RandomJoke>
}