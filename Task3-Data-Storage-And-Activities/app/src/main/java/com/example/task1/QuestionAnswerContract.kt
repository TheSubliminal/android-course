package com.example.task1

import android.provider.BaseColumns

object QuestionAnswerContract {
  object QuestionAnswerEntry : BaseColumns {
    const val TABLE_NAME = "question_answer"
    const val COLUMN_NAME_QUESTION = "question"
    const val COLUMN_NAME_ANSWER = "answer"
  }
}