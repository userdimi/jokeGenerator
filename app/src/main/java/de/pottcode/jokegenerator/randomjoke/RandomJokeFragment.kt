package de.pottcode.jokegenerator.randomjoke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
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

        randomJokeViewModel.randomJoke.observe(viewLifecycleOwner, Observer { randomJoke ->
            text_view_random_joke.text = randomJoke.joke
            button_get_random_joke.text = "Funny! Get another one"
        })

        randomJokeViewModel.isProgressbarVisible.observe(viewLifecycleOwner, Observer { value ->
            value.let { show ->
                progressBar_random_joke.visibility = if (show) View.VISIBLE else View.GONE
            }
        })

        randomJokeViewModel.snackbarText.observe(viewLifecycleOwner, Observer { text ->
            text?.let {
                Snackbar.make(view, text, Snackbar.LENGTH_LONG)
            }
        })

        button_get_random_joke.setOnClickListener {
            randomJokeViewModel.getRandomJokeFromApi()
        }
    }
}