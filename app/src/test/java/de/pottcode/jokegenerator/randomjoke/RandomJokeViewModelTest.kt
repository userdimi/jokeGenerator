package de.pottcode.jokegenerator.randomjoke

import de.pottcode.jokegenerator.repository.JokeGeneratorRepository
import de.pottcode.jokegenerator.utils.InstantTaskExecutorRule
import de.pottcode.jokegenerator.utils.MainCoroutineScopeRule
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    private lateinit var randomJokeViewModel: RandomJokeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        randomJokeViewModel = RandomJokeViewModel(mockRepository)
    }

    @Test
    fun verifyThatRandomJokeIsCalledFromDatabase() {
        // act
        randomJokeViewModel.randomJoke

        // assert
        verify { mockRepository.randomJoke }
    }

    @Test
    fun verifyBehaviorOfGetRandomJokeFromApi() {
        // act
        randomJokeViewModel.getRandomJokeFromApi()

        // assert
        coVerify { mockRepository.fetchRandomJokeFromNetwork() }
    }
}