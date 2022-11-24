package ru.gb.veber.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.gb.veber.customview.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.ticTacToeField.ticTacToeField = TicTacToeField(10, 10)
        binding.randomFieldButton.setOnClickListener {
            binding.ticTacToeField.ticTacToeField =
                TicTacToeField(Random.nextInt(3, 10), Random.nextInt(3, 10))
        }

    }
}