package com.example.reshi_primer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.widget.TextView
import android.widget.Button
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var totalQuestions : TextView
    private lateinit var correctAnswers : TextView
    private lateinit var incorrectAnswers : TextView
    private lateinit var score : TextView
    private lateinit var operand1 : TextView
    private lateinit var operand2 : TextView
    private lateinit var operator : TextView
    private lateinit var answer : TextView
    private lateinit var check : Button
    private lateinit var start : Button
    private var totalCorrect = 0
    private var totalIncorrect = 0
    private val operatorsList = listOf("+", "-", "*", "/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        totalQuestions = findViewById(R.id.totalQuestions)
        correctAnswers = findViewById(R.id.correctAnswers)
        incorrectAnswers = findViewById(R.id.incorrectAnswers)
        score = findViewById(R.id.score)
        operand1 = findViewById(R.id.operand1)
        operand2 = findViewById(R.id.operand2)
        operator = findViewById(R.id.operator)
        answer = findViewById(R.id.answer)
        check = findViewById<Button>(R.id.check)
        start = findViewById<Button>(R.id.start)

        answer.isEnabled = false
        check.isEnabled = false

        check.setOnClickListener {
            checkAnswer()
        }

        start.setOnClickListener {
            startQuiz()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkAnswer() {
        check.isEnabled = false
        start.isEnabled = true
        answer.isEnabled = false

        val correctAnswer = calculateAnswer().toString()

        if (answer.text.toString() == correctAnswer) {
            totalCorrect++
            answer.setBackgroundColor(Color.GREEN)
        }
        else {
            totalIncorrect++
            answer.setBackgroundColor(Color.RED)
        }
        correctAnswers.text = totalCorrect.toString()
        incorrectAnswers.text = totalIncorrect.toString()
        totalQuestions.text = (totalCorrect + totalIncorrect).toString()
        score.text = String.format("%.2f", (totalCorrect.toDouble() / (totalCorrect + totalIncorrect) * 100)) + "%"
    }

    private fun startQuiz() {
        start.isEnabled = false
        check.isEnabled = true
        answer.isEnabled = true
        answer.setBackgroundColor(Color.TRANSPARENT)

        generateNewExpression()
    }

    private fun generateNewExpression(){
        operand1.text = Random.nextInt(10, 100).toString()
        answer.text = ""
        operand2.text = Random.nextInt(10, 100).toString()
        operator.text = operatorsList[Random.nextInt(0, 4)]

        while (operator.text == "/" && operand1.text.toString().toInt() % operand2.text.toString().toInt() != 0)
        {
            operand1.text = Random.nextInt(10, 100).toString()
            operand2.text = Random.nextInt(10, 100).toString()
        }
    }

    private fun calculateAnswer() : Int {
        return when (operator.text){
            "+" -> {
                operand1.text.toString().toInt() + operand2.text.toString().toInt()
            }
            "-" -> {
                operand1.text.toString().toInt() - operand2.text.toString().toInt()
            }
            "*" -> {
                operand1.text.toString().toInt() * operand2.text.toString().toInt()
            }
            "/" -> {
                operand1.text.toString().toInt() / operand2.text.toString().toInt()
            }
            else -> 0
        }
    }
}