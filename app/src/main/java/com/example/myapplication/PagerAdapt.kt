package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import org.w3c.dom.Text

class PagerAdapt(val myContext:Context): PagerAdapter() {
    val Tips:ArrayList<Int> = arrayListOf(R.drawable.maskon,R.drawable.washhand,R.drawable.donttouch,R.drawable.keepdistance,
        R.drawable.keepclean,R.drawable.cover,R.drawable.urshirt,R.drawable.stayhome)
    override fun isViewFromObject(view: View, Obj: Any): Boolean {
        return view == Obj
    }

    override fun getCount(): Int {
        return Tips.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imgView = ImageView(myContext)
        imgView.setImageResource(Tips[position])
        container.addView(imgView,0)
        return  imgView
    }

    override fun destroyItem(container: ViewGroup, position: Int, Obj: Any) {
        container.removeView(Obj as View?)
    }
}