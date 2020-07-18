package de.pottcode.jokegenerator.randomjoke

import de.pottcode.jokegenerator.di.RetrofitModule
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * (c) Dimitri Simon on 08.07.20
 */
class RetrofitClientTest {

    @Test
    fun verifyBaseUrl() {
        assertTrue(RetrofitModule.baseUrl == "https://api.chucknorris.io/jokes/")
    }

}