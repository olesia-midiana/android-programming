import android.util.Log
import androidx.lifecycle.ViewModel

import com.bignerdranch.android.geomain.Question
import com.bignerdranch.android.geomain.R
import kotlin.math.max

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    var currentIndex = 0

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private val numberOfQuestions = questionBank.size
    private val isQuestionAnswered = BooleanArray(numberOfQuestions) { i -> false }
    private val isAnswerCorrect = BooleanArray(numberOfQuestions) { i -> false }

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
    val isCurrentQuestionAnswered: Boolean
        get() = isQuestionAnswered[currentIndex]
    val isLastQuestion: Boolean
        get() = !isQuestionAnswered.contains(false)
    val grade: Float
        get() = (isAnswerCorrect.count { it }.toFloat() / numberOfQuestions) * 100

    fun markQuestionAsAnswered(){
        isQuestionAnswered[currentIndex] = true
    }

    fun saveQuestionResultConclusion(conclusion: Boolean){
        isAnswerCorrect[currentIndex] = conclusion
    }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = max(0, currentIndex - 1) % questionBank.size
    }
}
