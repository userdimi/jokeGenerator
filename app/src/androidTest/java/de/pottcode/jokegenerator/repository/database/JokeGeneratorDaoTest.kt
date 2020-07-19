package de.pottcode.jokegenerator.repository.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.pottcode.jokegenerator.repository.model.RandomJoke
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * (c) Dimitri Simon on 19.07.20
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class JokeGeneratorDaoTest {

    private lateinit var jokeGeneratorDatabase: JokeGeneratorDatabase
    private lateinit var jokeGeneratorDao: JokeGeneratorDao
    private val context = InstrumentationRegistry.getInstrumentation().context
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testCoroutineDispatcher)

    @Before
    fun setUp() {
        jokeGeneratorDatabase =
            Room.inMemoryDatabaseBuilder(context, JokeGeneratorDatabase::class.java)
                .setTransactionExecutor(testCoroutineDispatcher.asExecutor())
                .setQueryExecutor(testCoroutineDispatcher.asExecutor())
                .build()
        jokeGeneratorDao = jokeGeneratorDatabase.randomJokeDao()
    }

    @After
    fun tearDown() {
        jokeGeneratorDatabase.close()
    }

    @Test
    fun verifyThatRandomJokeIsSavedToDB() {
        // arrange
        val randomJoke = createFirstRandomJoke()
        testScope.runBlockingTest {
            // assert
            assertNull(jokeGeneratorDao.getLastSavedRandomJoke())
            // act
            jokeGeneratorDao.saveRandomJoke(randomJoke)
            // assert
            assertEquals(randomJoke, jokeGeneratorDao.getLastSavedRandomJoke())
        }
    }

    @Test
    fun verifyThatTheLastSavedJokeIsLoaded() {
        // arrange
        val firstRandomJoke = createFirstRandomJoke()
        val secondRandomJoke = createSecondRandomJoke()

        testScope.runBlockingTest {
            // assert
            assertNull(jokeGeneratorDao.getLastSavedRandomJoke())
            // act
            jokeGeneratorDao.saveRandomJoke(firstRandomJoke)
            jokeGeneratorDao.saveRandomJoke(secondRandomJoke)
            // assert
            assertEquals(secondRandomJoke, jokeGeneratorDao.getLastSavedRandomJoke())
        }
    }


    private fun createFirstRandomJoke(): RandomJoke {
        return RandomJoke(
            "foo.de",
            "12345",
            "Funny stuff",
            "foo.bar.de",
            System.currentTimeMillis().toString()
        )
    }

    private fun createSecondRandomJoke(): RandomJoke {
        return RandomJoke(
            "foo.com",
            "54321",
            "Much more funny stuff",
            "foo.bar.com",
            (System.currentTimeMillis() + 1000).toString()
        )
    }
}