package com.example.task1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

const val EXTRA_QUESTION = "com.example.task1.QUESTION"
const val EXTRA_ANSWER = "com.example.task1.ANSWER"

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  fun displayQuestion(view: View) {
    val editQuestion = findViewById<EditText>(R.id.editQuestion)

    val question = editQuestion.text.toString()

    if (question == "") {
      showError()
    } else {
      hideError()
      displayValues(question)
    }
  }

  private fun showError() {
    val errorLabel = findViewById<TextView>(R.id.displayError)

    errorLabel.visibility = View.VISIBLE
  }

  private fun hideError() {
    val errorLabel = findViewById<TextView>(R.id.displayError)

    errorLabel.visibility = View.GONE
  }

  private fun displayValues(question: String) {
    val answerGroup = findViewById<RadioGroup>(R.id.answerGroup)
    val answerButton = findViewById<RadioButton>(answerGroup.checkedRadioButtonId)
    val questionLabel = findViewById<TextView>(R.id.displayQuestion)
    val answerLabel = findViewById<TextView>(R.id.displayAnswer)

    val answer = answerButton.text.toString()

    questionLabel.text = question
    questionLabel.visibility = View.VISIBLE

    answerLabel.text = answer
    answerLabel.visibility = View.VISIBLE
  }
}
