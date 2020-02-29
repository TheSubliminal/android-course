package com.example.task1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

private const val SQL_CREATE_ENTRIES =
  "CREATE TABLE ${QuestionAnswerContract.QuestionAnswerEntry.TABLE_NAME} (" +
    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
    "${QuestionAnswerContract.QuestionAnswerEntry.COLUMN_NAME_QUESTION} TEXT," +
    "${QuestionAnswerContract.QuestionAnswerEntry.COLUMN_NAME_ANSWER} TEXT)"

private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${QuestionAnswerContract.QuestionAnswerEntry.TABLE_NAME}"

class QuestionAnswerDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
  override fun onCreate(db: SQLiteDatabase) {
    db.execSQL(SQL_CREATE_ENTRIES)
  }

  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    db.execSQL(SQL_DELETE_ENTRIES)
    onCreate(db)
  }

  companion object {
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "QuestionAnswer.db"
  }
}