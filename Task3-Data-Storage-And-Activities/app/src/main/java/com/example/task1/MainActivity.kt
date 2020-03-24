package com.example.task1

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction

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
      setSaveInfoMessage(null)
    } else {
      val answerFragment = supportFragmentManager.findFragmentById(R.id.answerFragment) as AnswerFragment
      val answer = answerFragment.getAnswer()

      questionFragment.hideError()
      saveValues(question, answer)
    }
  }

  private fun setSaveInfoMessage(infoTextResId: Int?) {
    val saveInfoView = findViewById<TextView>(R.id.saveInfoMessage)

    if (infoTextResId == null) {
      saveInfoView.apply {
        text = ""
      }
    } else {
      saveInfoView.setText(infoTextResId)
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
      setSaveInfoMessage(R.string.save_success)
    } else {
      setSaveInfoMessage(R.string.save_error)
    }

    dbHelper.close()
    displayValues(question)
  }

  fun onOpenSavedValues(view: View) {
    val displayFragment = supportFragmentManager.findFragmentById(R.id.inputDisplayFragment) as InputDisplayFragment
    displayFragment.clearText()
    setSaveInfoMessage(null)


    val intent = Intent(this, InputDisplayActivity::class.java)
    startActivity(intent)
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
