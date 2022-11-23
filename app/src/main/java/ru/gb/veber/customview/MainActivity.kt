package ru.gb.veber.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.gb.veber.customview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        with(binding.bindingPathButton) {
            negativeButton.text = "Cansel"
            positiveButton.text = "Ok"
            positiveButton.setOnClickListener {
                progress.visibility = View.VISIBLE
                negativeButton.visibility = View.INVISIBLE
                positiveButton.visibility = View.INVISIBLE
            }
        }
    }
}