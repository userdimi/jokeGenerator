package de.pottcode.jokegenerator.randomjoke

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.pottcode.jokegenerator.repository.JokeGeneratorRepository
import de.pottcode.jokegenerator.repository.JokeGeneratorRepository.RandomJokeFetchError
import de.pottcode.jokegenerator.repository.model.RandomJoke
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class RandomJokeViewModel @ViewModelInject constructor(private val repository: JokeGeneratorRepository) :
    ViewModel() {

    val randomJoke: LiveData<RandomJoke>
        get() = _randomJoke

    private var _randomJoke = MutableLiveData<RandomJoke>()

    val isProgressbarVisible: LiveData<Boolean>
        get() = _isProgressbarVisible

    private var _isProgressbarVisible = MutableLiveData<Boolean>()

    val snackbarText: LiveData<String?>
        get() = _snackBarText

    private var _snackBarText = MutableLiveData<String?>()


    init {
        viewModelScope.launch {
            _randomJoke.value = repository.getRandomJokeFromDatabase()
        }
    }

    fun getRandomJokeFromApi() {
        fetchRandomJoke {
            _randomJoke.postValue(repository.getRandomJokeFromNetwork())
        }
    }

    private fun fetchRandomJoke(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _isProgressbarVisible.value = true
                block()
            } catch (error: RandomJokeFetchError) {
                _snackBarText.value = error.message
            } finally {
                _isProgressbarVisible.value = false
            }
        }
    }
}