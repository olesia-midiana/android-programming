package com.bignerdranch.android.geomain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.content.Context

private const val EXTRA_ANSWER_IS_TRUE =
    "com.bignerdranch.android.geomain.answer_is_true"

class CheatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}