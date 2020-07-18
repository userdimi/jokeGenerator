package de.pottcode.jokegenerator.randomjoke

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.pottcode.jokegenerator.repository.JokeGeneratorRepository
import de.pottcode.jokegenerator.repository.model.RandomJoke
import kotlinx.coroutines.launch


class RandomJokeViewModel @ViewModelInject constructor(private val repository: JokeGeneratorRepository) :
    ViewModel() {

    private var _randomJoke = MutableLiveData<RandomJoke>()

    val randomJoke: LiveData<RandomJoke>
        get() = _randomJoke

    fun getRandomJoke() {
        viewModelScope.launch {
            _randomJoke.value = repository.getRandomJoke()
        }
    }
}