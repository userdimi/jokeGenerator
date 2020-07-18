package de.pottcode.jokegenerator.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import de.pottcode.jokegenerator.repository.JokeGeneratorRepository
import de.pottcode.jokegenerator.repository.database.JokeGeneratorDao
import de.pottcode.jokegenerator.repository.network.JokeGeneratorService
import java.util.concurrent.Executor

/**
 * (c) Dimitri Simon on 12.07.20
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideRandomJokeRepository(
        jokeGeneratorService: JokeGeneratorService,
        jokeGeneratorDao: JokeGeneratorDao
    ): JokeGeneratorRepository {
        return JokeGeneratorRepository(
            jokeGeneratorService,
            jokeGeneratorDao
        )
    }

    @Provides
    @ActivityRetainedScoped
    fun provideExecutor(): Executor {
        return Executor {}
    }
}