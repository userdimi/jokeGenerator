package de.pottcode.jokegenerator.randomjoke

import de.pottcode.jokegenerator.repository.JokeGeneratorRepository
import de.pottcode.jokegenerator.repository.model.RandomJoke
import de.pottcode.jokegenerator.utils.InstantTaskExecutorRule
import de.pottcode.jokegenerator.utils.MainCoroutineScopeRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * (c) Dimitri Simon on 19.07.20
 */

@ExperimentalCoroutinesApi
class RandomJokeViewModelTest {


    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var mockRepository: JokeGeneratorRepository

    @MockK
    private lateinit var mockRandomJoke: RandomJoke
    private lateinit var randomJokeViewModel: RandomJokeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        randomJokeViewModel = RandomJokeViewModel(mockRepository)
    }

    @Test
    fun verifyThatGetRandomJokeIsCalled() {
        // act
        randomJokeViewModel.getRandomJoke()

        // assert
        verify {
            runBlockingTest {
                mockRepository.getRandomJoke()
            }
        }
    }

    @Test
    fun verifyRandomJokeLiveData() {
        // arrange
        coEvery {
            mockRepository.getRandomJoke()
        } returns mockRandomJoke

        // act
        randomJokeViewModel.getRandomJoke()

        // assert
        assertEquals(mockRandomJoke, randomJokeViewModel.randomJoke.value)
    }


}