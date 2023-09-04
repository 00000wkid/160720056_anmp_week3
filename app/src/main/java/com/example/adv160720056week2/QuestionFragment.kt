package com.example.adv160720056week2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation

class QuestionFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var point = 0
        val operator = arrayOf("+", "-", "*", "/")
        val question = view.findViewById<TextView>(R.id.txtQuestion)
        val Answer = view.findViewById<EditText>(R.id.txtAnswer)
        val btnSubmit = view.findViewById<Button>(R.id.btnSubmit)
        fun generateNewQuestion() {
            val numberA = (0..100).random()
            val numberB = (0..100).random()
            val arrNumber = (0..3).random()
            val operatorSymbol = operator[arrNumber]

            val combine = "$numberA $operatorSymbol $numberB"
            question.text = combine
            Answer.text = null
        }
        generateNewQuestion()

       //dapet stack overflow
        val regex = """^-?\d+(\.\d{0,2})?$""".toRegex()

        btnSubmit.setOnClickListener {
            val userAnswerText = Answer.text.toString()

           //ngecek klo sama
            if (userAnswerText.matches(regex)) {
                val userAnswer = userAnswerText.toDoubleOrNull()

                if (userAnswer != null) {
                    val currentQuestion = question.text.toString()
                    val parts = currentQuestion.split(" ")
                    val numberA = parts[0].toDouble()
                    val operatorSymbol = parts[1]
                    val numberB = parts[2].toDouble()
                    val result = when (operatorSymbol) {
                        "+" -> numberA + numberB
                        "-" -> numberA - numberB
                        "*" -> numberA * numberB
                        "/" -> if (numberB != 0.0) numberA / numberB else Double.POSITIVE_INFINITY // Handle division by zero
                        else -> Double.NaN
                    }

                    if (Math.abs(userAnswer - result) <= 0.01) {
                        point++
                        Toast.makeText(requireContext(), "Correct Answer!!", Toast.LENGTH_SHORT).show()
                        generateNewQuestion()
                    } else {
                        Toast.makeText(requireContext(), "Wrong Answer!!", Toast.LENGTH_SHORT).show()
                        val action = QuestionFragmentDirections.actionResultFragment(point.toString())
                        Navigation.findNavController(it).navigate(action)
                    }
                } else {
                    Toast.makeText(requireContext(), "Invalid Input", Toast.LENGTH_SHORT).show()

                }
            } else {
                Toast.makeText(requireContext(), "Wrong Input!!\n if result is 0.12345 just answer 0.12", Toast.LENGTH_SHORT).show()
            }
        }
    }





}


