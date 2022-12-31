package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore : Int): ViewModel() {

    private var _score = MutableLiveData<Int>()
    val score : LiveData<Int>
        get() = _score

    init {
        Log.i("ScoreViewModel","The Score is $finalScore")
        _score.value=finalScore
    }
}