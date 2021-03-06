package com.example.task1


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

/**
 * A simple [Fragment] subclass.
 */
class AnswerFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_answer, container, false)
  }

  fun getAnswer(): String {
    val answerGroup = view?.findViewById<RadioGroup>(R.id.answerGroup)
    val answerButton = view?.findViewById<RadioButton>(answerGroup!!.checkedRadioButtonId)

    return answerButton?.text.toString()
  }
}
