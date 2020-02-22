package com.example.task1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction

const val EXTRA_QUESTION = "com.example.task1.QUESTION"
const val EXTRA_ANSWER = "com.example.task1.ANSWER"

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  fun onDisplayInputValues(view: View) {
    val questionFragment = supportFragmentManager.findFragmentById(R.id.questionFragment) as QuestionFragment
    val question = questionFragment.getQuestion()

    if (question == "") {
      questionFragment.showError()
    } else {
      questionFragment.hideError()
      displayValues(question)
    }
  }

  private fun displayValues(question: String) {
    val answerFragment = supportFragmentManager.findFragmentById(R.id.answerFragment) as AnswerFragment
    val answer = answerFragment.getAnswer()

    var displayFragment = supportFragmentManager.findFragmentById(R.id.inputDisplayFragment) as InputDisplayFragment
    if (InputDisplayFragment.composeText(question, answer) != displayFragment.displayText) {
      displayFragment = InputDisplayFragment.newInstance(question, answer)

      supportFragmentManager.beginTransaction().apply {
        replace(R.id.inputDisplayFragment, displayFragment)
        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        commit()
      }
    }
  }
}
