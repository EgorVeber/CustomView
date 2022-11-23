package ru.gb.veber.customview

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresApi
import ru.gb.veber.customview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val token = Any()
    private val handler = Handler(Looper.getMainLooper())

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        binding.bottomButtons.setListener {
            if (it == BottomButtonAction.POSITIVE) {
                binding.bottomButtons.isProgressMode = true
                handler.postDelayed({
                    binding.bottomButtons.isProgressMode = false
                    binding.bottomButtons.setPositiveButtonText("Updated OK")
                    Toast.makeText(this, "Positive Button Pressed", Toast.LENGTH_SHORT).show()
                }, token, 2000)

            } else if (it == BottomButtonAction.NEGATIVE) {
                binding.bottomButtons.setNegativeButtonText("Updated CANCEL")
                Toast.makeText(this, "NEGATIVE Button Pressed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(token)
    }
}