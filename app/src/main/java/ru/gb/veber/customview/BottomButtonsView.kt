package ru.gb.veber.customview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import ru.gb.veber.customview.databinding.PartButtonsBinding

//Какой root такой и наследний.
//merge чтобы небыло 2 Constraint
class BottomButtonsView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAtr: Int,
    defStyleRes: Int,
) : ConstraintLayout(context, attrs, defStyleAtr, defStyleRes) {

    private val binding: PartButtonsBinding

    //Используется когд анадо создать компаннент с стандартным стилем (из темы например если переопределили).
    //Если нет стиля ни в теме ни в XML тогда будет братся styleRes можно задать default стиль.
    // Если все есть то только не переопредленные атрибуты будут братся из нашего default стиль если вы его написали.
    constructor(context: Context, attrs: AttributeSet?, defStyleAtr: Int) : this(context,
        attrs,
        defStyleAtr,
        0)

    //Можно указать стандартный стиль вместо 0 или стиль из темы если 0 то вроде ничего не будет братся
    //Вызается в xml. attrs это из xml которые.
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    //Этот constructor дли создания из кода обычно тоесть никогда
    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.part_buttons, this, true)//Наши атрибуты
        binding = PartButtonsBinding.bind(this)
        initializeAttributes(attrs, defStyleAtr, defStyleRes)
    }

    private fun initializeAttributes(attrs: AttributeSet?, defStyleAtr: Int, defStyleRes: Int) {
        if (attrs == null) return
        // Надо распарить наши атрибуты и обработать
        val typedArray = context.obtainStyledAttributes(attrs,
            R.styleable.BottomButtonsView,
            defStyleAtr,
            defStyleRes)
        with(binding) {
            val positiveButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_bottomPositiveButtonText)
            positiveButton.text = positiveButtonText ?: "Ok"

            val negativeButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_bottomNegativeButtonText)
            negativeButton.text = negativeButtonText ?: "Cancel"

            val positiveBottomBgColor =
                typedArray.getColor(R.styleable.BottomButtonsView_bottomPositiveBackgroundColor,
                    Color.BLACK)
            positiveButton.backgroundTintList = ColorStateList.valueOf(positiveBottomBgColor)

            val negativeBottomBgColor =
                typedArray.getColor(R.styleable.BottomButtonsView_bottomNegativeBackgroundColor,
                    Color.WHITE)
            negativeButton.backgroundTintList = ColorStateList.valueOf(negativeBottomBgColor)


            val positiveTextBgColor =
                typedArray.getColor(R.styleable.BottomButtonsView_bottomPositiveTextColor,
                    Color.WHITE)
            positiveButton.setTextColor(positiveTextBgColor)


            val isProgressMod =
                typedArray.getBoolean(R.styleable.BottomButtonsView_bottomProgressMode, false)

            if (isProgressMod) {
                positiveButton.visibility = INVISIBLE
                negativeButton.visibility = INVISIBLE
                progress.visibility = VISIBLE
            } else {
                positiveButton.visibility = VISIBLE
                negativeButton.visibility = VISIBLE
                progress.visibility = GONE
            }
        }

        typedArray.recycle()// Очищаем ресурсы
    }
}