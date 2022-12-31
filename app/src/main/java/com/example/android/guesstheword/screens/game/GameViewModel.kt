package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlin.concurrent.timer


class GameViewModel : ViewModel() {

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 60000L
    }

    private lateinit var timer : CountDownTimer
    // The current time
    private var _newTime = MutableLiveData<String>() //nullable+Mutable+inaccessible outside livedata, currently is null
    val newTime : LiveData<String>                   // it's uneditable+accessible outside
        get() = _newTime


    // The current word
    private var _word = MutableLiveData<String>() //nullable+Mutable+inaccessible outside livedata, currently is null
    val word : LiveData<String>                   // it's uneditable+accessible outside
            get() = _word

    // The current score
    private var _score = MutableLiveData<Int>()      //nullable+Mutable+inaccessible outside livedata, currently it is null
    val score : LiveData<Int>                        //it's uneditable+accessible
        get() = _score

    //event game finish
    private var _eventGameFinish=MutableLiveData<Boolean>()
    val eventGameFinish : LiveData<Boolean>
        get() = _eventGameFinish

    // The list of words - the front of the list is the next word to guess
    lateinit var wordList: MutableList<String>


    init {
        Log.i("GameViewModel","GameViewModel is created")

        _score.value=0   // init live data of score
        _word.value=""   // init live data of word
        _eventGameFinish.value = false

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _newTime.value=DateUtils.formatElapsedTime(millisUntilFinished)
            }

            override fun onFinish() {
                _eventGameFinish.value=true
            }
        }
        timer.start()

        resetList()
        nextWord()
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
            //resetList()
            _eventGameFinish.value=true
        }
        else
        {
            timer.cancel()
            timer.start()
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

    fun onGameFinishCompleted()
    {
        _eventGameFinish.value=false
    }


    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel","GameViewModel is destroyed")
        timer.cancel()
    }
}