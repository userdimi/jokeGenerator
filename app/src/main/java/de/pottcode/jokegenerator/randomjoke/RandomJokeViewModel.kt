package de.pottcode.jokegenerator.randomjoke

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.pottcode.jokegenerator.repository.JokeGeneratorRepository
import de.pottcode.jokegenerator.repository.JokeGeneratorRepository.RandomJokeFetchException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class RandomJokeViewModel @ViewModelInject constructor(private val repository: JokeGeneratorRepository) :
    ViewModel() {

    val randomJoke = repository.randomJoke

    val isProgressbarVisible: LiveData<Boolean>
        get() = _isProgressbarVisible

    private var _isProgressbarVisible = MutableLiveData<Boolean>()

    val snackbarText: LiveData<String?>
        get() = _snackBarText

    private var _snackBarText = MutableLiveData<String?>()


    fun getRandomJokeFromApi() {
        fetchRandomJoke {
            repository.fetchRandomJokeFromNetwork()
        }
    }

    private fun fetchRandomJoke(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _isProgressbarVisible.value = true
                block()
            } catch (exception: RandomJokeFetchException) {
                _snackBarText.value = exception.message
            } finally {
                _isProgressbarVisible.value = false
            }
        }
    }
}