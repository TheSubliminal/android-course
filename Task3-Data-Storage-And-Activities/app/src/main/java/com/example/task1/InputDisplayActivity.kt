package com.example.task1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction

class InputDisplayActivity : AppCompatActivity() {
  private var composedText = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_display_question)

    getQuestionAnswerValues()
    initializeView()
  }

  private fun getQuestionAnswerValues() {
    val dbHelper = QuestionAnswerDbHelper(baseContext)
    val db = dbHelper.readableDatabase

    val projection = arrayOf(
      QuestionAnswerContract.QuestionAnswerEntry.COLUMN_NAME_QUESTION,
      QuestionAnswerContract.QuestionAnswerEntry.COLUMN_NAME_ANSWER
    )

    val sortOrder = "${BaseColumns._ID} DESC"

    val cursor = db.query(
      QuestionAnswerContract.QuestionAnswerEntry.TABLE_NAME,   // The table to query
      projection,             // The array of columns to return (pass null to get all)
      null,                   // The columns for the WHERE clause
      null,                   // The values for the WHERE clause
      null,                   // don't group the rows
      null,                   // don't filter by row groups
      sortOrder               // The sort order
    )

    with(cursor) {
      if (cursor.moveToLast()) {
        var question = getString(0)
        var answer = getString(1)
        composedText = "$question\n$answer\n\n"

        while (cursor.moveToPrevious()) {
          question = getString(0)
          answer = getString(1)

          composedText += "$question\n$answer\n\n"
        }
        hideError()
      } else {
        showError()
      }
    }

    cursor.close()
    dbHelper.close()
  }

  private fun initializeView() {
    var displayFragment = supportFragmentManager.findFragmentById(R.id.questionAnswerFragment) as InputDisplayFragment
    if (InputDisplayFragment.composeText(composedText, "") != displayFragment.displayText) {
      displayFragment = InputDisplayFragment.newInstance(composedText, "")

      supportFragmentManager.beginTransaction().apply {
        replace(R.id.questionAnswerFragment, displayFragment)
        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        commit()
      }
    }
  }

  private fun showError() {
    findViewById<TextView>(R.id.noValuesMessage).apply {
      visibility = View.VISIBLE
    }
  }

  private fun hideError() {
    findViewById<TextView>(R.id.noValuesMessage).apply {
      visibility = View.GONE
    }
  }
}
