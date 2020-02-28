package com.example.task1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  fun onSaveInputValues(view: View) {
    val questionFragment = supportFragmentManager.findFragmentById(R.id.questionFragment) as QuestionFragment
    val question = questionFragment.getQuestion()

    if (question == "") {
      questionFragment.showError()
    } else {
      questionFragment.hideError()
      saveValues(question)
    }
  }

  private fun saveValues(question: String) {
    val answerFragment = supportFragmentManager.findFragmentById(R.id.answerFragment) as AnswerFragment
    val answer = answerFragment.getAnswer()


  }

  fun onOpenSavedValues(view: View) {
    val intent = Intent(this, InputDisplayActivity::class.java)
    startActivity(intent)
  }
}
