package com.example.cointoss

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val animatorList= mutableListOf<Animator>()
    private var x:Float = 1160f *-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        val imgCoin = findViewById<ImageView>(R.id.coin_imageView)
        val msg = findViewById<TextView>(R.id.result_textView)

        animatorList.add(imgCoin.doTranslationX())
        animatorList.add(imgCoin.doTranslationXR())
        animatorList.add(msg.scaleX())
        animatorList.add(msg.scaleY())

        button.setOnClickListener {
            msg.scaleX= 0F
            when(Random.nextBoolean()){
                true -> msg.text = "オモテ"
                else -> msg.text = "ウラ"
            }
            imgCoin.doRotationX()
            AnimatorSet().let {
                it.playSequentially(animatorList)
                it.start()
            }
        }
    }
    private fun View.doRotationX (){
        ObjectAnimator.ofFloat(this,"rotationX",0f,360f*30)
                .setDuration(1400L)
               .start()
    }
    private fun View.doTranslationX():Animator{
        var objectAnimator= ObjectAnimator.ofFloat(this,"translationY",0f,-1160f)
        objectAnimator.duration = 800L
        return objectAnimator
    }

    private fun View.doTranslationXR():Animator{
        var objectAnimator= ObjectAnimator.ofFloat(this,"translationY",-1160f,0f)
        objectAnimator.duration = 500L
        return objectAnimator
    }
    private fun View.scaleX():Animator{
        return ObjectAnimator.ofFloat(this,"scaleX",0f,3f).setDuration(200L)
    }
    private fun View.scaleY():Animator{
        return ObjectAnimator.ofFloat(this,"scaleY",0f,3f).setDuration(200L)
    }
}