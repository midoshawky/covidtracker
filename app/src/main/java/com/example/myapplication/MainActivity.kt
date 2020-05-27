package com.example.myapplication

import android.app.ActionBar
import android.content.Intent
import android.content.res.Resources
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import okhttp3.OkHttpClient


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    var appName:LinearLayout ?=null
    var tipsPager:ViewPager ?=null
    var tipsPagerAdapter : PagerAdapt ?=null
    var tipsTxt : TextView ?= null
    var nxtTip:Button ?= null
    val TipsTxt:ArrayList<String> = arrayListOf("Firstly,Don`t forget wearing mask","Wash your hands regularly","Stop touching your face","Keep distance", "Keep clean", "Cover coughs and sneezed","Use shirt arm when cough","Finally, Please stay at home")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent: Intent = Intent(this,MapViewTraker::class.java)
        val fadeIn:Animation = AnimationUtils.loadAnimation(this,R.anim.fadein)
        val popOut:Animation = AnimationUtils.loadAnimation(this,R.anim.nav_default_pop_enter_anim)
        val slide:Animation = AnimationUtils.loadAnimation(this,R.anim.slide)
        val appLogo:ImageView = findViewById(R.id.applogo)
        val pagerLayout:FrameLayout = findViewById(R.id.tipslay)
        nxtTip = findViewById(R.id.nxt)
        tipsPager = findViewById(R.id.tips)
        tipsTxt = findViewById(R.id.tipscontent)
        tipsPagerAdapter = PagerAdapt(this)
        appName = findViewById(R.id.appname)
        tipsPager!!.adapter = tipsPagerAdapter
        tipsTxt!!.text = TipsTxt[0]
        appLogo.startAnimation(fadeIn)
        pagerLayout.animate().translationX(0F).setDuration(2000).setStartDelay(3000).start()
        nxtTip!!.setOnClickListener {
            if (tipsPager!!.currentItem < tipsPagerAdapter!!.count-1){
                tipsPager!!.currentItem = tipsPager!!.currentItem+1
            }else{
                startActivity(intent)
            }
        }
        tipsPager!!.addOnPageChangeListener( object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                println("pos : $position, count = ${tipsPagerAdapter!!.count}")
                tipsTxt!!.text = TipsTxt[position]
                if(position == tipsPagerAdapter!!.count-1){
                    nxtTip!!.text = resources.getText(R.string.done)
                    nxtTip!!.compoundDrawablePadding=0
                }else{
                    nxtTip!!.text = resources.getText(R.string.next)
                }
            }

        })
    }
}


