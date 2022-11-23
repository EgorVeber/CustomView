package ru.gb.veber.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ru.gb.veber.customview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        binding.bottomButtons.setListener {
            if (it == BottomButtonAction.POSITIVE) {
                Toast.makeText(this, "Positive Button Pressed", Toast.LENGTH_SHORT).show()
            } else if (it == BottomButtonAction.NEGATIVE) {
                Toast.makeText(this, "NEGATIVE Button Pressed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}