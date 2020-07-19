package de.pottcode.jokegenerator.randomjoke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import de.pottcode.jokegenerator.R
import kotlinx.android.synthetic.main.random_joke_fragment.*

@AndroidEntryPoint
class RandomJokeFragment : Fragment() {

    private val randomJokeViewModel: RandomJokeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.random_joke_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        randomJokeViewModel.randomJoke.observe(viewLifecycleOwner, Observer {
            text_view_random_joke.text = it.joke
            button_get_random_joke.text = "Funny! Get another one"
        })

        button_get_random_joke.setOnClickListener {
            randomJokeViewModel.getRandomJokeFromApi()
        }
    }
}