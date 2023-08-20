/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.example.android.unscramble.ui.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.unscramble.R
import com.example.android.unscramble.databinding.GameFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.math.absoluteValue

/**
 * Fragment where the game is played, contains the game logic.
 */
class GameFragment : Fragment() {

    // Binding object instance with access to the views in the game_fragment.xml layout
    private lateinit var binding: GameFragmentBinding

    // Creating a ViewModel instance of the GameViewModel in the UI controller to act as a reference the first time the fragment is created.
    private val viewModel: GameViewModel by viewModels()
    // If the fragment is re-created, it receives the same GameViewModel instance created by the
    // first fragment

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout XML file and return a binding object instance
        binding = GameFragmentBinding.inflate(inflater, container, false)
         return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Log.d("GameFragment", "GameFragment created/re-created!")
//        Log.d("GameFragment", "Word: ${viewModel.currentScrambledWord}" +
//                "Score: ${viewModel.score} WordCount: ${viewModel.currentWordCount}")

        viewModel.gameStates.observe(viewLifecycleOwner){
            when(it){
                is GameStates.Error->{
                    setErrorTextField(true)
                }
                is GameStates.NextRound ->{
                    setErrorTextField(false)
                    binding.score.text=it.score.toString()
                    binding.wordCount.text= "Trial number:  ${it.numberOfWords.toString()} of 10 words"
                    binding.textViewUnscrambledWord.text=it.word
                }
                is GameStates.GameCompleted ->{
                    showFinalScoreDialog(it.score)
                }
            }
        }

        // Setup a click listener for the Submit and Skip buttons.
        binding.submit.setOnClickListener { viewModel.compareWord(binding.textInputEditText.text.toString()) }
        binding.skip.setOnClickListener { viewModel.newGame(false) }
        // Update the UI


    }
    override fun onDetach() {
        super.onDetach()
        Log.d("GameFragment", "GameFragment destroyed!")
    }

    private fun showFinalScoreDialog(score:Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.you_scored, score))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) { _, _ ->
                exitGame()
            }
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                viewModel.newGame(true)
            }
            .show()
    }


    /*
     * Skips the current word without changing the score.
     * Increases the word count.
     */



    /*
     * Gets a random word for the list of words and shuffles the letters in it.
     */


    /*
     * Re-initializes the data in the ViewModel and updates the views with the new data, to
     * restart the game.
     */


    /*
     * Exits the game.
     */
    private fun exitGame() {
        activity?.finish()
    }

    /*
    * Sets and resets the text field error status.
    */
    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }

    /*
     * Displays the next scrambled word on screen.
     */

}


/*Syntax learned

    1. Initialize a ViewModel using the by viewModels() Kotlin property delegate.
    2. // Syntax for property delegation
        var <property-name> : <property-type> by <delegate-class>()
    3. A ViewModel is not destroyed if its owner is destroyed for a configuration change, such as screen rotation.
       The new instance of the owner reconnects to the existing ViewModel instance.
    4. The ViewModel is destroyed when the associated fragment is detached, or when the activity is finished.
 */

//Drafts
//        currentScrambledWord = getNextScrambledWord()
//        currentWordCount++
//        score += SCORE_INCREASE
//        binding.wordCount.text = getString(R.string.word_count, currentWordCount, MAX_NO_OF_WORDS)
//        binding.score.text = getString(R.string.score, score)
//        setErrorTextField(false)
//        updateNextWordOnScreen()

//private fun onSkipWord() {
////        currentScrambledWord = getNextScrambledWord()
////        currentWordCount++
////        binding.wordCount.text = getString(R.string.word_count, currentWordCount, MAX_NO_OF_WORDS)
////        setErrorTextField(false)
////        updateNextWordOnScreen()
//    }