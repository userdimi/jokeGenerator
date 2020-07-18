package de.pottcode.jokegenerator.randomjoke.repository

import de.pottcode.jokegenerator.repository.JokeGeneratorRepository
import de.pottcode.jokegenerator.repository.database.JokeGeneratorDao
import de.pottcode.jokegenerator.repository.network.JokeGeneratorService
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

/**
 * (c) Dimitri Simon on 08.07.20
 */
class JokeGeneratorRepositoryTest {

    private lateinit var jokeGeneratorRepository: JokeGeneratorRepository

    @MockK
    private lateinit var mockJokeGeneratorService: JokeGeneratorService

    @MockK
    private lateinit var mockJokeGeneratorDao: JokeGeneratorDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        jokeGeneratorRepository =
            JokeGeneratorRepository(
                mockJokeGeneratorService,
                mockJokeGeneratorDao
            )
    }

    @Ignore("Should be fixed in the next push")
    @Test
    fun verifyRandomJokeIsGettingFromTheRepo() {
        // assert
        var expectedJoke = ""

        // act
        expectedJoke = ""

        // assert
        assertTrue(expectedJoke.isNotEmpty())
    }
}