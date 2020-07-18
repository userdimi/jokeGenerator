package de.pottcode.jokegenerator.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import de.pottcode.jokegenerator.repository.database.JokeGeneratorDatabase
import de.pottcode.jokegenerator.repository.database.JokeGeneratorDao
import javax.inject.Singleton

/**
 * (c) Dimitri Simon on 12.07.20
 */
@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(application: Application): JokeGeneratorDatabase {
        return Room.databaseBuilder(
            application,
            JokeGeneratorDatabase::class.java,
            "joke_generator_db"
        ).build()
    }

    @Singleton
    @Provides
    fun providesRandomJokeDao(jokeGeneratorDatabase: JokeGeneratorDatabase): JokeGeneratorDao {
        return jokeGeneratorDatabase.randomJokeDao()
    }
}