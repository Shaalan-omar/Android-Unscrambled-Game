package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

val MAX_SIZE=11

sealed class GameStates{
    object Error:GameStates()
    data class GameCompleted(val score:Int):GameStates()
    data class NextRound(val word:String,val score:Int,val numberOfWords:Int):GameStates()
}

class GameViewModel: ViewModel() {

    private val _gameStateLiveData: MutableLiveData<GameStates> = MutableLiveData()
    val gameStates: LiveData<GameStates> get() = _gameStateLiveData
    private var currentScore: Int = 0
    private var counter: Int = 0

    private var randomWord = ""

    init {
        newGame(true)
    }

    fun newGame(resetScore: Boolean) {
        if (resetScore) {
            counter = 0
            currentScore = 0
        }
        val tempRandomWord = getRandomWord()
        _gameStateLiveData.value = GameStates.NextRound(tempRandomWord, currentScore, counter)
        counter++
        if (counter == MAX_SIZE) {
            _gameStateLiveData.value = GameStates.GameCompleted(currentScore)
        }
    }

    private fun getRandomWord(): String {
        val tempRandomWord = allWordsList.random()
        var shuffleWord: CharArray = charArrayOf()
        shuffleWord = tempRandomWord.toCharArray()
        while (tempRandomWord.equals(String(shuffleWord), false)) {

            shuffleWord.shuffle()
        }

        randomWord = tempRandomWord
        return String(shuffleWord)
    }

    fun compareWord(word: String) {
        if (word.lowercase() == randomWord) {
            getRandomWord()
            currentScore++
            counter++
            if (counter == MAX_SIZE) {
                _gameStateLiveData.value = GameStates.GameCompleted(currentScore)
            } else {
                val shuffledWord = getRandomWord()
                _gameStateLiveData.value = GameStates.NextRound(shuffledWord, currentScore, counter)
            }
        } else {
            _gameStateLiveData.value = GameStates.Error
        }

    }
}


//DRAFTSSS

//    private var wordsList: MutableList<String> = mutableListOf()
//    private lateinit var currentWord: String
//
//    private val _currentScrambledWord: MutableLiveData<String> = MutableLiveData()
//    val currentScrambledWord : LiveData<String> get() = _currentScrambledWord
//
//    private val _score: MutableLiveData<Int> = MutableLiveData()
//    val score : LiveData<Int> get() = _score
//
//    private val _currentWordCount: MutableLiveData<Int> = MutableLiveData()
//    val currentWordCount : LiveData<Int> get() = _currentWordCount
//
//
//    private fun getNextWord(){
//        currentWord = allWordsList.random()
//        val tempWord = currentWord.toCharArray()
//        while (String(tempWord).equals(currentWord, false)) {
//            tempWord.shuffle()
//        }
//        if (wordsList!= null && wordsList.contains(currentWord)) {
//            getNextWord()
//        } else {
//            _currentScrambledWord.value = String(tempWord)
//            _currentWordCount.value = _currentWordCount.value!! + 1 //Error
//            wordsList.add(currentWord)
//        }
//    }
//    fun nextWord(): Boolean {
//        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
//            getNextWord()
//            true
//        } else false
//    }
//    private fun increaseScore() {
//        _score.value = _score.value?.plus(SCORE_INCREASE)
//    }
//    fun isUserWordCorrect(playerWord: String): Boolean {
//        if (playerWord.equals(currentWord, true)) {
//            increaseScore()
//            return true
//        }
//        return false
//    }
//    fun reinitializeData() {
//        _score.value = 0
//        _currentWordCount.value = 0
//        wordsList.clear()
//        getNextWord()
//    }
//    init {
//        Log.d("GameFragment", "GameViewModel created!")
//        getNextWord() //Error
//    }
//
//
//
//    override fun onCleared() {
//        super.onCleared()
//        Log.d("GameFragment", "GameViewModel destroyed!")
//    }






//Drafts
//class GameViewModel: ViewModel() {
//    init {
//        Log.d("GameFragment", "GameViewModel created!")
//    }
//
//    private var score = 0
//    private var currentWordCount = 0
//    private var _currentScrambledWord = "test"
//    private var wordList: MutableList<String> = allWordsList
//    private lateinit var currentWord: String
//    val currentScrambledWord: String get() = _currentScrambledWord
//
//
//    override fun onCleared() {
//        super.onCleared()
//        Log.d("GameFragment", "GameViewModel destroyed!")
//    }

//fun getNextWord(): String {
//    currentWord = wordList.random() //Getting a random word
//    val trueWord = currentWord //Assigning it before it is shuffled to be used
//    val tempWord = currentWord.toCharArray() //Converting to chars
//    tempWord.shuffle() //Shuffling the chars
//
//
//    if (tempWord.toString() == trueWord) //Handling the case for having the shuffled word the same as the original one
//    {
//        tempWord.shuffle() //Shuffling the chars once again
//        currentWord = tempWord.toString()
//        wordList.drop(wordList.indexOf(trueWord)) //Removing the word so from the list
//        return currentWord
//        //I may replace every variable of trueWord with current word w khalas
//    }
//    else
//    {
//        return currentWord
//    }
//
//}
//}


