package de.pottcode.jokegenerator.randomjoke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
        subscribeTextView()
        subscribeProgressBar()
        subscribeSnackbar(view)
        setButtonClickListener()
    }

    private fun subscribeTextView() {
        randomJokeViewModel.randomJoke.observe(viewLifecycleOwner, Observer { randomJoke ->
            if (randomJoke == null) {
                text_view_random_joke.text =
                    getString(R.string.empty_joke)
            } else {
                text_view_random_joke.text = randomJoke.joke
            }
        })
    }

    private fun subscribeProgressBar() {
        randomJokeViewModel.isProgressbarVisible.observe(viewLifecycleOwner, Observer { show ->
            if (show) {
                button_get_random_joke.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.button_background
                    )
                )
                progressBar_random_joke.visibility = View.VISIBLE
            } else {
                progressBar_random_joke.visibility = View.GONE
                button_get_random_joke.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.dark_text_color
                    )
                )
            }
        })
    }

    private fun subscribeSnackbar(view: View) {
        randomJokeViewModel.snackbarText.observe(viewLifecycleOwner, Observer { text ->
            text?.let {
                Snackbar.make(view, text, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun setButtonClickListener() {
        button_get_random_joke.setOnClickListener {
            randomJokeViewModel.getRandomJokeFromApi()
        }
    }
}