package com.bignerdranch.android.geomain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.view.Gravity
import android.view.View
import android.util.Log
import kotlin.math.max

import androidx.lifecycle.ViewModelProvider
import QuizViewModel

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private val numberOfQuestions = questionBank.size
    private val isQuestionAnswered = BooleanArray(numberOfQuestions) { i -> false }
    private val isAnswerCorrect = BooleanArray(numberOfQuestions) { i -> false }

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        // ViewModelProviders is deprecated
        //val provider: ViewModelProvider = ViewModelProviders.of(this)
        //val quizViewModel = provider.get(QuizViewModel::class.java)

        val quizViewModelFactory = ViewModelProvider.NewInstanceFactory()
        val quizViewModel: QuizViewModel = ViewModelProvider(
            this,
            quizViewModelFactory).get(QuizViewModel::class.java)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }
        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        prevButton.setOnClickListener {
            currentIndex = max(0, currentIndex - 1) % questionBank.size
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
        trueButton.isEnabled = !isQuestionAnswered[currentIndex]
        falseButton.isEnabled = !isQuestionAnswered[currentIndex]
    }

    private fun checkAnswer(userAnswer: Boolean){
        trueButton.isEnabled = false
        falseButton.isEnabled = false
        isQuestionAnswered[currentIndex] = true

        val correctAnswer = questionBank[currentIndex].answer
        isAnswerCorrect[currentIndex] = userAnswer == correctAnswer


        if (isQuestionAnswered.contains(false)) {
            val messageResId = if (userAnswer == correctAnswer) {
                R.string.correct_toast
            } else {
                R.string.incorrect_toast
            }
            var toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM, 0, 0)
            toast.show()
        } else {
            val correctAnswersCount = isAnswerCorrect.count { it }
            val grade = (correctAnswersCount.toFloat() / numberOfQuestions) * 100
            val gradeString = String.format("%.2f", grade) + " / 100"
            val messageGrade = resources.getString(R.string.grade_toast) + " " + gradeString
            var toast = Toast.makeText(this, messageGrade, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM, 0, 0)
            toast.show()
        }
    }
}