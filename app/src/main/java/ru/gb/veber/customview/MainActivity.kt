package ru.gb.veber.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*
import ru.gb.veber.customview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        binding.bottomButtons.setListener {
            if (it == BottomButtonAction.POSITIVE) {
                scope.launch {
                    binding.bottomButtons.isProgressMode = true
                    delay(2000)
                    binding.bottomButtons.isProgressMode = false
                    binding.bottomButtons.setPositiveButtonText("Updated OK")
                }
            } else if (it == BottomButtonAction.NEGATIVE) {
                binding.bottomButtons.setNegativeButtonText("Updated CANCEL")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}