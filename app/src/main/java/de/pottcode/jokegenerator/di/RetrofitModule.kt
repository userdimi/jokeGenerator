package de.pottcode.jokegenerator.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import de.pottcode.jokegenerator.repository.network.JokeGeneratorService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * (c) Dimitri Simon on 08.07.20
 */
@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    var baseUrl = "https://api.chucknorris.io/jokes/"


    @Singleton
    @Provides
    fun providesRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(providesOkHttpClient())
            .build()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun providesJokeGeneratorService(retrofit: Retrofit): JokeGeneratorService {
        return retrofit.create(JokeGeneratorService::class.java)
    }


}