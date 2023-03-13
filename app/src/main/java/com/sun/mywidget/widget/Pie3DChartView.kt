package com.sun.mywidget.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.sun.mywidget.R
import com.sun.mywidget.bean.DataBean
import com.sun.mywidget.bean.PieBean
import com.sun.mywidget.expand.logD
import kotlin.math.cos
import kotlin.math.sin

class Pie3DChartView : View {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val mPaint by lazy {
        Paint().also {
            it.color = Color.BLUE
            it.isAntiAlias = true
            it.style = Paint.Style.FILL
        }
    }

    //110,90,70,50,30,10
    private val pieList = mutableListOf<PieBean>()

    init {
        pieList.add(PieBean("报警视频", 215f, 110f, 11, context.getColor(R.color.bg_EF13FC)))
        pieList.add(PieBean("五类", 125f, 90f, 9, context.getColor(R.color.bg_6B38FD)))
        pieList.add(PieBean("误报", 325f, 70f, 7, context.getColor(R.color.bg_35D0E9)))
        pieList.add(PieBean("三类", 35f, 50f, 5, context.getColor(R.color.bg_5CA755)))
        pieList.add(PieBean("四类", 95f, 30f, 3, context.getColor(R.color.bg_F7D172)))
        pieList.add(PieBean("紧急报警", 85f, 10f, 1, context.getColor(R.color.bg_D91F1B)))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //确定底部的圆的位置
        //宽度为控件宽的一半，高度为控件高度的三分之一
        val bcLeft = (width / 4).toFloat()
        val bcTop = (height / 8 * 3).toFloat()
        val bcRight = (width / 4 * 3).toFloat()
        val bcBottom = (height / 8 * 7).toFloat()
        val bottomCircle = RectF()

        //绘制第一层
        bottomCircle.set(bcLeft - 30, bcTop - 30, bcRight + 30, bcBottom + 30)
        mPaint.color = context.getColor(R.color.bg_122042)
        canvas?.drawOval(bottomCircle, mPaint)

        //绘制第二层
        bottomCircle.set(bcLeft - 20, bcTop - 20, bcRight + 20, bcBottom + 20)
        mPaint.color = context.getColor(R.color.bg_11224D)
        canvas?.drawOval(bottomCircle, mPaint)

        //绘制第三层
        bottomCircle.set(bcLeft - 10, bcTop - 10, bcRight + 10, bcBottom + 10)
        mPaint.color = context.getColor(R.color.bg_11275A)
        canvas?.drawOval(bottomCircle, mPaint)

        bottomCircle.set(bcLeft, bcTop, bcRight, bcBottom)
        for (bean in pieList) {
            mPaint.color = bean.color
            canvas?.drawArc(bottomCircle, bean.start, bean.sweep, true, mPaint)
        }

        for (bean in pieList) {
            for (h in 0 until bean.height) {
                bottomCircle.set(bcLeft, bcTop - h, bcRight, bcBottom - h)
                mPaint.color = bean.color
                canvas?.drawArc(bottomCircle, bean.start, bean.sweep, true, mPaint)
            }
        }

        val topCX = (bcRight - bcLeft) / 2 + bcLeft
        val topCY = (bcBottom - bcTop) / 2 + bcTop
        val rS = (bcBottom - bcTop) / 4
        val rL = (bcRight - bcLeft) / 4
//        for (bean in pieList) {
        val bean = pieList[1]
        val cx = getCenterX(bean, rS, rL)
        val cy = getCenterY(bean, rS, rL)
        val cirX = topCX + cx
        val cirY = topCY - bean.height + cy
        "cx=$cx;cy=$cy;cirX=$cirX;cirY=$cirY;".logD()
        mPaint.color = Color.WHITE
        canvas?.drawCircle(cirX, cirY, 5f, mPaint)
//        }
    }

    private var maxH = 0

    fun setData(list: List<DataBean>) {
        if (list.isNullOrEmpty()) {
            "数据集合为空".logD()
            return
        }
        val total = getTotal(list)
        if (total == 0) {
            "总数量为0".logD()
            return
        }
        if (pieList.size > 0) {
            pieList.clear()
        }
        val sortList = list.sortedByDescending { bean -> bean.num }
        var startL = 0f
        var startR = 0f
        for (index in sortList.indices) {
            val bean = sortList[index]
            if (bean != null) {
                val sweep = getSweep(bean.num, total)
                var start = 0f
                if (index == 0) {
                    maxH = bean.num
                    startL = 270f - sweep / 2
                    startR = 270f + sweep / 2
                    start = startL
                } else {
                    if (judgeStart(startL, startR, sweep)) {
                        startL -= sweep
                        start = startL
                    } else {
                        start = startR
                        startR += sweep
                        if (startR >= 360f) {
                            startR -= 360f
                        }
                    }
                }
                pieList.add(
                    PieBean(
                        bean.name,
                        start,
                        sweep,
                        getPieHeight(bean.num),
                        getColor(bean.name)
                    )
                )
            }
        }
        if (pieList.size > 0) {
            postInvalidate()
        }
    }

    private fun judgeStart(l: Float, r: Float, s: Float): Boolean {
        //以270为界 谁小取谁
        val s1 = 270f - (l - s)//左边角度
        var s2 = r + s//右边角度
        if (s2 >= 360f) {
            s2 -= 270f
        } else {
            s2 += 90f
        }
        return s1 <= s2
    }

    private fun getTotal(list: List<DataBean>): Int {
        var res = 0
        for (bean in list) res += bean.num
        return res
    }

    private fun getSweep(number: Int, total: Int): Float {
        return number * 360f / total
    }

    private fun getPieHeight(num: Int): Int {
        return layoutParams.height * num / 3 / maxH
    }

    private fun getColor(name: String): Int {
        return when (name) {
            "视频报警" -> context.getColor(R.color.bg_EF13FC)
            "五类" -> context.getColor(R.color.bg_6B38FD)
            "误报" -> context.getColor(R.color.bg_35D0E9)
            "三类" -> context.getColor(R.color.bg_5CA755)
            "四类" -> context.getColor(R.color.bg_F7D172)
            "紧急报警" -> context.getColor(R.color.bg_D91F1B)
            else -> context.getColor(R.color.bg_white)
        }
    }

    private fun getRadius(angle: Float): Boolean {
        var res = true
        if (angle <= 45f) {
            res = false
        } else if (angle <= 135f) {
            res = true
        } else if (angle <= 225f) {
            res = false
        } else if (angle <= 315f) {
            res = true
        } else if (angle <= 360f) {
            res = false
        }
        return res
    }

    private fun getCenterX(bean: PieBean, rs: Float, rL: Float): Float {
        var res = 0.0
        val angle = bean.start + bean.sweep / 2
        if (angle <= 90f) {
            res = cos(Math.toRadians(angle.toDouble()))
        } else if (angle <= 180f) {
            res = -sin(Math.toRadians((angle - 90f).toDouble()))
        } else if (angle <= 270f) {
            res = -cos(Math.toRadians((angle - 180f).toDouble()))
        } else if (angle <= 360f) {
            res = cos(Math.toRadians((360f - angle).toDouble()))
        }
        res *= if (getRadius(angle)) {
            rs
        } else {
            rL
        }
        return res.toFloat()
    }

    private fun getCenterY(bean: PieBean, rs: Float, rL: Float): Float {
        var res = 0.0
        val angle = bean.start + bean.sweep / 2
        if (angle <= 90f) {
            res = sin(Math.toRadians(angle.toDouble()))
        } else if (angle <= 180f) {
            res = cos(Math.toRadians((angle - 90f).toDouble()))
        } else if (angle <= 270f) {
            res = -sin(Math.toRadians((angle - 180f).toDouble()))
        } else if (angle <= 360f) {
            res = -sin(Math.toRadians((360f - angle).toDouble()))
        }
        res *= if (getRadius(angle)) {
            rs
        } else {
            rL
        }
        return res.toFloat()
    }
}