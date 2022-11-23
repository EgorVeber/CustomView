package ru.gb.veber.customview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import ru.gb.veber.customview.databinding.PartButtonsBinding

//Какой root такой и наследний.
//merge чтобы небыло 2 Constraint


enum class BottomButtonAction {
    POSITIVE, NEGATIVE
}

typealias OnBottomButtonsActionListener = (BottomButtonAction) -> Unit

class BottomButtonsView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAtr: Int,
    defStyleRes: Int,
) : ConstraintLayout(context, attrs, defStyleAtr, defStyleRes) {

    private val binding: PartButtonsBinding
    private var listener: OnBottomButtonsActionListener? = null

    var isProgressMode: Boolean = false
        //По умолчанию
        set(value) {
            field = value

            if (value) {
                binding.positiveButton.visibility = INVISIBLE
                binding.negativeButton.visibility = INVISIBLE
                binding.progress.visibility = VISIBLE
            } else {
                binding.positiveButton.visibility = VISIBLE
                binding.negativeButton.visibility = VISIBLE
                binding.progress.visibility = GONE
            }
        }

    //Используется когд анадо создать компаннент с стандартным стилем (из темы например если переопределили).
    //Если нет стиля ни в теме ни в XML тогда будет братся styleRes можно задать default стиль.
    // Если все есть то только не переопредленные атрибуты будут братся из нашего default стиль если вы его написали.
    constructor(context: Context, attrs: AttributeSet?, defStyleAtr: Int) : this(context,
        attrs,
        defStyleAtr,
        R.style.MyBottomButtonsStyle)

    //Можно указать стандартный стиль вместо 0(3тий параметр) или стиль из темы если 0 то вроде ничего не будет братся
    //Вызается в xml. attrs это из xml которые.
    constructor(context: Context, attrs: AttributeSet?) : this(context,
        attrs,
        R.attr.bottomButtonStyle)

    //Этот constructor дли создания из кода обычно тоесть никогда
    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.part_buttons, this, true)//Наши атрибуты
        binding = PartButtonsBinding.bind(this)
        initializeAttributes(attrs, defStyleAtr, defStyleRes)
        initListeners()
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
            setPositiveButtonText(positiveButtonText)

            val negativeButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_bottomNegativeButtonText)
            setNegativeButtonText(negativeButtonText)

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
                    Color.WHITE)//Когда нету 4 атрибутов либо они не заданы.
            positiveButton.setTextColor(positiveTextBgColor)


            this@BottomButtonsView.isProgressMode =
                typedArray.getBoolean(R.styleable.BottomButtonsView_bottomProgressMode, false)

        }

        typedArray.recycle()// Очищаем ресурсы
    }

    private fun initListeners() {
        binding.positiveButton.setOnClickListener {
            this.listener?.invoke(BottomButtonAction.POSITIVE)
        }
        binding.negativeButton.setOnClickListener {
            this.listener?.invoke(BottomButtonAction.NEGATIVE)
        }
    }

    fun setListener(listener: OnBottomButtonsActionListener?) {
        this.listener = listener
    }

    fun setPositiveButtonText(text: String?) {
        binding.positiveButton.text = text ?: "OK"
    }

    fun setNegativeButtonText(text: String?) {
        binding.negativeButton.text = text ?: "Cancel"
    }


    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()!!
        val savedState = SavedState(superState)
        savedState.positiveButtonText = binding.positiveButton.text.toString()
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as SavedState
        super.onRestoreInstanceState(savedState.superState)
        binding.positiveButton.text = savedState.positiveButtonText
    }


    class SavedState : BaseSavedState {
        var positiveButtonText: String? = null

        constructor(superState: Parcelable) : super(superState)
        constructor(parcel: Parcel) : super(parcel) {
            positiveButtonText = parcel.readString()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(positiveButtonText)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(p0: Parcel): SavedState {
                    return SavedState(p0)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return Array(size) { null }
                }
            }
        }
    }
}