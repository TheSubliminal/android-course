package com.example.task1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DisplayQuestionActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_display_question)

    val question = intent.getStringExtra(EXTRA_QUESTION)
    val answer = intent.getStringExtra(EXTRA_ANSWER)

    val questionView = findViewById<TextView>(R.id.questionView).apply {
      text = question
    }
    val answerView = findViewById<TextView>(R.id.answerView).apply {
      text = answer
    }
  }
}
