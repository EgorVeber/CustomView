package ru.gb.veber.customview

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import java.lang.reflect.Type
import kotlin.math.max
import kotlin.math.min
import kotlin.properties.Delegates

class TicTacToeView(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int,
) : View(context, attributeSet, defStyleAttr, defStyleRes) {

    //set для перерисовок
    var ticTacToeField: TicTacToeField? = null
        set(value) {
            field?.listeners?.remove(listener)
            field = value
            value?.listeners?.add(listener)
            updatedViewSize()
            requestLayout()//Если еще и размеры будут менятся
            invalidate()//запускает перерисовку
        }

    private var player1Color by Delegates.notNull<Int>()
    private var player2Color by Delegates.notNull<Int>()
    private var gridColor by Delegates.notNull<Int>()

    constructor(context: Context, attrs: AttributeSet?, defStyleAtr: Int) : this(context,
        attrs,
        defStyleAtr,
        R.style.GlobalTicTacToeFieldStyle)

    constructor(context: Context, attrs: AttributeSet?) : this(context,
        attrs,
        R.attr.ticTacToeFieldStyle)

    constructor(context: Context) : this(context, null)

    init {
        if (attributeSet != null) {
            intiAttributes(attributeSet, defStyleAttr, defStyleRes)
        } else {
            initDefaultColors()
        }
    }

    private fun initDefaultColors() {
        player1Color = PLAYER1_DEFAULT_COLOR
        player2Color = PLAYER2_DEFAULT_COLOR
        gridColor = GRID_DEFAULT_COLOR
    }

    private fun intiAttributes(attributesSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val typedArray = context.obtainStyledAttributes(attributesSet,
            R.styleable.TicTacToeView,
            defStyleAttr,
            defStyleRes)
        player1Color =
            typedArray.getColor(R.styleable.TicTacToeView_player1Color, PLAYER1_DEFAULT_COLOR)
        player2Color =
            typedArray.getColor(R.styleable.TicTacToeView_player2Color, PLAYER2_DEFAULT_COLOR)
        gridColor = typedArray.getColor(R.styleable.TicTacToeView_gridColor, GRID_DEFAULT_COLOR)

        typedArray.recycle()
    }

    private val listener: OnFieldChangedListener = {

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ticTacToeField?.listeners?.add(listener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ticTacToeField?.listeners?.remove(listener)
    }


    //Можем задать размеры ВЫзываетс яокгда нужно измерить размер view.Компоновщик договорится с ним
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // Не строгие ограничение suggestedMinimumWidth -
        // Предположительная ширина берется от минимального background + minWidth
        val minWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val minHeight = suggestedMinimumHeight + paddingBottom + paddingTop
        val desiredCellSizePixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            DESIRED_CELL_SIZE, resources.displayMetrics).toInt()
        val rows = ticTacToeField?.rows ?: 0
        val columns = ticTacToeField?.columns ?: 0

        //Вя область с паддинагми
        val desiredWith =
            max(minWidth, columns * desiredCellSizePixels + paddingLeft + paddingRight)
        val desiredHeight =
            max(minHeight, rows * desiredCellSizePixels + paddingTop + paddingBottom)

        //Всегда 3 ограничителя любой , не больше компановшика, и только который задал компановщик
        setMeasuredDimension(resolveSize(desiredWith, widthMeasureSpec),
            resolveSize(desiredHeight, heightMeasureSpec))
    }


    private val fieldRect = RectF(0f, 0f, 0f, 0f)
    private var cellSize: Float = 0f//размер ячейки
    private var cellPadding: Float = 0f//размер паддинка ячейки

    //Вызывается когда уже есть размеры. И говорим где будем рисовать.
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updatedViewSize()
    }

    private fun updatedViewSize() {
        val field = this.ticTacToeField ?: return

        //безопастные
        val safeWidth = width - paddingLeft - paddingRight
        val safeHeight = height - paddingTop - paddingBottom


        val cellWidth = safeWidth / field.columns.toFloat()
        val cellHeight = safeHeight / field.rows.toFloat()

        cellSize = min(cellWidth, cellHeight)

        cellPadding = cellSize * 0.2f

        val fieldWight = cellSize * field.columns
        val fieldHeight = cellSize * field.rows


        //По центру и обалсть рисования
        fieldRect.left = paddingLeft + (safeWidth - fieldWight) / 2
        fieldRect.top = paddingTop + (safeHeight - fieldHeight) / 2
        fieldRect.right = fieldRect.left + fieldWight
        fieldRect.bottom = fieldRect.top + fieldHeight
    }

    companion object {
        const val PLAYER1_DEFAULT_COLOR = Color.GREEN
        const val PLAYER2_DEFAULT_COLOR = Color.RED
        const val GRID_DEFAULT_COLOR = Color.GRAY
        const val DESIRED_CELL_SIZE = 50f
    }
}