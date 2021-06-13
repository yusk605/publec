package com.example.cointoss


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.random.Random

class CoinTossSurface @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr),SurfaceHolder.Callback,Runnable {

    private var canvas:Canvas? = null
    private var job:Job?= null

    private var mainLoop:java.lang.Thread? = null
    private var isLoop:Boolean=true
    // 円のX,Y座標
    private var circleX = 0
    private var circleY = 0

    // 円の移動量
    private var circleVx = 5
    private var circleVy = 5

    private val p = Paint()
    private val p2 = Paint()

    init {
        p.color = Color.BLACK
        p2.color = Color.BLUE
        p2.style=Paint.Style.FILL_AND_STROKE
        p.style = Paint.Style.FILL_AND_STROKE
        holder.addCallback(this)
      //  mainLoop = Thread(this)
       // mainLoop!!.start()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
        @Suppress("UNREACHABLE_CODE")
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
               isLoop = true
                Log.d("test","タップされました${isLoop}")
            }
            else -> {
            }
        }
        return true
    }

    private fun draw(holder: SurfaceHolder) {
         GlobalScope.launch(Dispatchers.Default) {
             while (true) {
                 val canvas = getHolder().lockCanvas()
                 if (canvas != null) {
                     canvas.drawColor(Color.WHITE)
                     // 円を描画する
                     canvas.drawCircle(circleX.toFloat(), circleY.toFloat(), 100f, p)
                     canvas.drawCircle(circleX.toFloat()+15,circleY.toFloat()+21,20f,p2)
                     getHolder().unlockCanvasAndPost(canvas)
                     // 円の座標を移動させる
                     circleX += circleVx
                     circleY += circleVy
                     // 画面の領域を超えた？
                     if (circleX < 0 || width < circleX) circleVx *= -1
                     if (circleY < 0 || height < circleY) circleVy *= -1
                 }
             }
         }

    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        val canvas = holder.lockCanvas()
        canvas.drawColor(Color.WHITE)
        holder.unlockCanvasAndPost(canvas)
        draw(holder)
    }
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) {}
    override fun run() {
        while (isLoop) {
            if (canvas != null) {
                val canvas = holder.lockCanvas()
                canvas.drawColor(Color.WHITE)
                // 円を描画する
                canvas.drawCircle(circleX.toFloat(), circleY.toFloat(), 100f, p)
                holder.unlockCanvasAndPost(canvas)
                // 円の座標を移動させる
                circleX += circleVx
                circleY += circleVy
                // 画面の領域を超えた？
                if (circleX < 0 || width < circleX) circleVx *= -1
                if (circleY < 0 || height < circleY) circleVy *= -1
            }
           if(Random.nextInt(10000) <= 10){
               //holder.unlockCanvasAndPost(canvas)
               isLoop = isLoop == false
           }
        }
    }
}