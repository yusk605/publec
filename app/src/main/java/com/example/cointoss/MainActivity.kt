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

/**
 * @author コイントスに判断をゆだねるもの。
 * master ギットの勉強として確認事項を行います。
 */
class MainActivity : AppCompatActivity() {

    //アニメーションを順番に実行させるためにリストを用意
    private val animatorList= mutableListOf<Animator>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val coin = findViewById<ImageView>(R.id.coin_imageView)
        val msg = findViewById<TextView>(R.id.result_textView)

       animatorList.also {
           it.add(coin.toAnimator(Property.TranslationY.get,0f,-1160f,800L))
           it.add(coin.toAnimator(Property.TranslationY.get,-1160f,0f,500L))
           it.add(msg.toAnimator(Property.ScaleX.get,0f,3f,200L))
           it.add(msg.toAnimator(Property.ScaleY.get,0f,3f,200L))
       }

        button.setOnClickListener {
            //アニメーションが終わるまで非表示にするため
            msg.scaleX= 0F
            //ギットの練習として。現在のバージョンは1.0です。
            when(Random.nextBoolean()){
                true -> msg.text = "オモテ"
                else -> msg.text = "ウラ"
            }

            //縦に回転するアニメーションは他のアニメーションと同時に並行してもよいため。
            coin.toAnimator(Property.RotationX.get,0f,360f*30,1400L).start()

            //アニメーションを順番に実行する、この時動作する順番はリストに入れた順番となる
            //アニメーションの同時に実行してしまうのを防ぐため
            AnimatorSet().let {
                it.playSequentially(animatorList)
                it.start()
            }

        }
    }

    /**
     * 今回使用するアニメーションのオブジェクトを取得
     * @param property 列挙型からプロパティを選択 Property
     * @param before   アニメーションする時の最初の位置
     * @param after    アニメーションするときの最後の位置
     * @param duration アニメーションする時間
     */
    private fun View.toAnimator(property:String,before:Float,after:Float,duration:Long):Animator =
            ObjectAnimator.ofFloat(this,property,before,after).setDuration(duration)

    /**
     * アニメーションとなる属性名を列挙型でまとめました。
     */
    enum class Property(val get:String){
            RotationX("rotationX"),
            TranslationY("translationY"),
            ScaleX("scaleX"),
            ScaleY("scaleY")
    }
}