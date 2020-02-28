package com.example.task1


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

const val TEXT_KEY = "TEXT"

/**
 * A simple [Fragment] subclass.
 */
class InputDisplayFragment : Fragment() {
  val displayText: String by lazy {
    arguments?.getString(TEXT_KEY) ?: ""
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    val view = inflater.inflate(R.layout.fragment_input_display, container, false)
    val displayInputsView = view.findViewById<TextView>(R.id.displayInputs)
    displayInputsView.text = displayText

    return view
  }

  companion object {
    fun newInstance(question: String, answer: String): InputDisplayFragment {
      val fragment = InputDisplayFragment()

      val composedText = composeText(question, answer)

      val args = Bundle()
      args.putString(TEXT_KEY, composedText)

      fragment.arguments = args

      return fragment
    }

    fun composeText(question: String, answer: String): String {
      return "$question\n$answer"
    }
  }
}
