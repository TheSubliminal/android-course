package com.example.task1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class InputDisplayActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_display_question)

    /*val savedValuesView = findViewById<TextView>(R.id.savedValuesView).apply {
      text = question
    }*/
  }
}
