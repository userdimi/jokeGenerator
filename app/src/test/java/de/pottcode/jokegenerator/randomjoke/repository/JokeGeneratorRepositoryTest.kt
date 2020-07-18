package de.pottcode.jokegenerator.randomjoke.repository

import de.pottcode.jokegenerator.di.RetrofitModule
import de.pottcode.jokegenerator.repository.JokeGeneratorRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * (c) Dimitri Simon on 08.07.20
 */
class JokeGeneratorRepositoryTest {

    private lateinit var jokeGeneratorRepository: JokeGeneratorRepository

    @MockK
    private lateinit var mockRetrofitModule: RetrofitModule

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        jokeGeneratorRepository =
            JokeGeneratorRepository(
                mockRetrofitModule
            )
    }

    @Test
    fun verifyRandomJokeIsGettingFromTheRepo() {
        // assert
        var expectedJoke = ""

        // act
        expectedJoke = jokeGeneratorRepository.fetchNewRandomJoke()

        // assert
        assertTrue(expectedJoke.isNotEmpty())
    }
}