package com.example.task1

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

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
      setSaveInfoMessage("")
    } else {
      val answerFragment = supportFragmentManager.findFragmentById(R.id.answerFragment) as AnswerFragment
      val answer = answerFragment.getAnswer()

      questionFragment.hideError()
      saveValues(question, answer)
    }
  }

  private fun setSaveInfoMessage(infoText: String) {
    findViewById<TextView>(R.id.saveInfoMessage).apply {
      text = infoText
    }
  }

  private fun saveValues(question: String, answer: String) {
    val dbHelper = QuestionAnswerDbHelper(baseContext)
    val db = dbHelper.writableDatabase

    val values = ContentValues().apply {
      put(QuestionAnswerContract.QuestionAnswerEntry.COLUMN_NAME_QUESTION, question)
      put(QuestionAnswerContract.QuestionAnswerEntry.COLUMN_NAME_ANSWER, answer)
    }

    val newId = db?.insert(QuestionAnswerContract.QuestionAnswerEntry.TABLE_NAME, null, values)
    if (newId != -1L) {
      setSaveInfoMessage(R.string.save_success.toString())
    } else {
      setSaveInfoMessage(R.string.save_error.toString())
    }

    dbHelper.close()
  }

  fun onOpenSavedValues(view: View) {
    setSaveInfoMessage("")

    val intent = Intent(this, InputDisplayActivity::class.java)
    startActivity(intent)
  }
}
