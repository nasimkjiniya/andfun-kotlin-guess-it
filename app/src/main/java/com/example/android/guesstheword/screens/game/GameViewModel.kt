package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class GameViewModel : ViewModel() {

    // The current word
    private var _word = MutableLiveData<String>() //nullable+Mutable+inaccessible outside livedata, currently is null
    val word : LiveData<String>                   // it's uneditable+accessible outside
            get() = _word



    // The current score
    private var _score = MutableLiveData<Int>()      //nullable+Mutable+inaccessible outside livedata, currently it is null
    val score : LiveData<Int>                        //it's uneditable+accessible
        get() = _score

    // The list of words - the front of the list is the next word to guess
    lateinit var wordList: MutableList<String>

    init {
        Log.i("GameViewModel","GameViewModel is created")
        resetList()
        nextWord()
        _score.value=0   // init live data of score
        _word.value=""   // init live data of word
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            //gameFinished()
        } else {
            _word.value = wordList.removeAt(0)
        }
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value=(score.value)?.minus(1) //null safety check
        nextWord()
    }

    fun onCorrect() {
        _score.value=(score.value)?.plus(1) //null safety check
        nextWord()
    }


    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel","GameViewModel is destroyed")
    }
}