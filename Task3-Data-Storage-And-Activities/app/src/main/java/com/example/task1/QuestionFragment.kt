package com.example.task1


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView

class QuestionFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_question, container, false)
  }

  fun showError() {
    val errorLabel = view?.findViewById<TextView>(R.id.errorMessage)
    errorLabel?.visibility = View.VISIBLE
  }

  fun hideError() {
    val errorLabel = view?.findViewById<TextView>(R.id.errorMessage)
    errorLabel?.visibility = View.GONE
  }

  fun getQuestion(): String {
    val questionInput = view?.findViewById<EditText>(R.id.questionInput)
    return questionInput?.text.toString()
  }

}
