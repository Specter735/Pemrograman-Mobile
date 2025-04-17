package com.example.diceroller

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val diceImage01: ImageView = findViewById(R.id.Dice1)
        diceImage01.setImageResource(R.drawable.dice_0)

        val diceImage02: ImageView = findViewById(R.id.Dice2)
        diceImage02.setImageResource(R.drawable.dice_0)

        val rollButton: Button = findViewById(R.id.Buttonn)

        rollButton.setOnClickListener { diceRoller() }
    }

    private fun diceRoller() {
        val dice1 = Dice(6)
        val diceRoll1 = dice1.roll()

        val diceImage1: ImageView = findViewById(R.id.Dice1)
        when (diceRoll1) {
            1 -> diceImage1.setImageResource(R.drawable.dice_1)
            2 -> diceImage1.setImageResource(R.drawable.dice_2)
            3 -> diceImage1.setImageResource(R.drawable.dice_3)
            4 -> diceImage1.setImageResource(R.drawable.dice_4)
            5 -> diceImage1.setImageResource(R.drawable.dice_5)
            6 -> diceImage1.setImageResource(R.drawable.dice_6)
        }

        val dice2 = Dice(6)
        val diceRoll2 = dice2.roll()

        val diceImage2: ImageView = findViewById(R.id.Dice2)
        when (diceRoll2) {
            1 -> diceImage2.setImageResource(R.drawable.dice_1)
            2 -> diceImage2.setImageResource(R.drawable.dice_2)
            3 -> diceImage2.setImageResource(R.drawable.dice_3)
            4 -> diceImage2.setImageResource(R.drawable.dice_4)
            5 -> diceImage2.setImageResource(R.drawable.dice_5)
            6 -> diceImage2.setImageResource(R.drawable.dice_6)
        }

        if (diceRoll1 == diceRoll2){
            val toast = Toast.makeText(this, "Selamat anda dapat dadu double!", Toast.LENGTH_SHORT)
            toast.show()
        } else {
            val toast = Toast.makeText(this, "Anda belum beruntung!", Toast.LENGTH_SHORT)
            toast.show() }

    }
}

class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}

