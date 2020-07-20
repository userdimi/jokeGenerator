package de.pottcode.jokegenerator.randomjoke.repository

import de.pottcode.jokegenerator.repository.JokeGeneratorRepository
import de.pottcode.jokegenerator.repository.database.JokeGeneratorDao
import de.pottcode.jokegenerator.repository.model.RandomJoke
import de.pottcode.jokegenerator.repository.network.JokeGeneratorService
import de.pottcode.jokegenerator.utils.MainCoroutineScopeRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertFailsWith

/**
 * (c) Dimitri Simon on 08.07.20
 */
@ExperimentalCoroutinesApi
class JokeGeneratorRepositoryTest {

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    private lateinit var jokeGeneratorRepository: JokeGeneratorRepository

    @MockK
    private lateinit var mockJokeGeneratorService: JokeGeneratorService

    @MockK
    private lateinit var mockJokeGeneratorDao: JokeGeneratorDao

    @MockK
    private lateinit var mockRandomJokeResponse: Response<RandomJoke>

    @MockK
    private lateinit var mockRandomJoke: RandomJoke

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        jokeGeneratorRepository =
            JokeGeneratorRepository(
                mockJokeGeneratorService,
                mockJokeGeneratorDao
            )
    }

    @Test
    fun verifyRandomJokeIsSavingIntoDBOnSuccess() {
        // arrange
        coEvery {
            mockJokeGeneratorService.getRandomJoke()
        } returns mockRandomJokeResponse

        every {
            mockRandomJokeResponse.isSuccessful
        } returns true

        every {
            mockRandomJokeResponse.body()
        } returns mockRandomJoke

        // act
        runBlockingTest {
            jokeGeneratorRepository.fetchRandomJokeFromNetwork()
        }

        // assert
        coVerify {
            mockJokeGeneratorDao.saveRandomJoke(mockRandomJoke)
        }
    }

    @Test
    fun verifyRandomJokeIsGettingFromDB() {
        // act
        jokeGeneratorRepository.randomJoke

        // assert
        verify { mockJokeGeneratorDao.getLastSavedRandomJoke() }
    }

    @Test
    fun verifyFetchRandomJokeException() {
        coEvery {
            mockJokeGeneratorService.getRandomJoke()
        } throws Throwable()
        // act
        assertFailsWith<JokeGeneratorRepository.RandomJokeFetchException> {
            runBlockingTest {
                jokeGeneratorRepository.fetchRandomJokeFromNetwork()
            }
        }
    }
}