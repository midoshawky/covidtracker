package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import java.lang.Exception
import kotlin.math.ceil

class RVAdapter(
    context:Context,
    arrayList: ArrayList<CountriesData>?
): RecyclerView.Adapter<RVAdapter.myViewHolder>() {
    var myArr:ArrayList<CountriesData>?=null
    var context:Context?=null
    var expanded = false
    init {
        this.context=context
        this.myArr=arrayList
    }
    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryText:TextView = itemView.findViewById(R.id.country)
        val confrimedText:TextView = itemView.findViewById(R.id.confrmd)
        val recoverdText:TextView = itemView.findViewById(R.id.rcvred)
        val deathText:TextView = itemView.findViewById(R.id.dth)
        val totconfrimedText:TextView = itemView.findViewById(R.id.totC)
        val totrecoverdText:TextView = itemView.findViewById(R.id.totR)
        val totdeathText:TextView = itemView.findViewById(R.id.totD)
        val seeMore:TextView = itemView.findViewById(R.id.seemore)
        val recoveryBar:ProgressBar = itemView.findViewById(R.id.recovery)
        val recoveryRate:TextView = itemView.findViewById(R.id.recoveryrate)
        val recoverLay:FrameLayout = itemView.findViewById(R.id.frameLay)
        val totalInfo:TableRow = itemView.findViewById(R.id.totalinfo)
        val cardLayout:CardView = itemView.findViewById(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
       val inflater:LayoutInflater= LayoutInflater.from(context)
       val view:View = inflater.inflate(R.layout.cards,parent,false)
       return myViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myArr!!.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val item=holder.countryText
        val confirmed = holder.confrimedText
        val recoverd = holder.recoverdText
        val dead = holder.deathText
        val totalConfirmed = holder.totconfrimedText
        val totalRecoverd = holder.totrecoverdText
        val totalDead = holder.totdeathText

        val moreInfo = holder.seeMore
        val recovery = holder.recoveryBar
        val recoverRate = holder.recoveryRate
        val cardView = holder.cardLayout
        val totalInfoLay = holder.totalInfo
        val recoverLay = holder.recoverLay
        var recoveryRate : String = ""

        recovery.scaleY = 3f;
        item.text = myArr?.get(position)?.country.toString()
        confirmed.text = myArr?.get(position)?.confrirmed.toString()
        recoverd.text = myArr?.get(position)?.recoverd.toString()
        dead.text = myArr?.get(position)?.death.toString()
        totalConfirmed.text = myArr?.get(position)?.totalCnfrm.toString()
        totalRecoverd.text = myArr?.get(position)?.totalRec.toString()
        totalDead.text = myArr?.get(position)?.totalDed.toString()
        try {
            recoveryRate = ceil((((myArr?.get(position)?.totalRec.toString()).toDouble() / (myArr?.get(position)?.totalCnfrm.toString()).toDouble())*100)).toString()
            println("rec : ${myArr?.get(position)?.totalRec.toString().toDouble()}")
            recoverRate.text = "Recover rate $recoveryRate%"
            recovery.progress = ((((myArr?.get(position)?.totalRec.toString()).toDouble() / (myArr?.get(position)?.totalCnfrm.toString()).toDouble())*100).toInt())
        }catch (e:Exception){
            println("Error")
        }

        item.setOnClickListener {
            Toast.makeText(context,"$position  ",Toast.LENGTH_LONG).show()
        }
        moreInfo.setOnClickListener {
            if(!expanded){
                recovery.visibility = View.INVISIBLE
                totalInfoLay.visibility = View.INVISIBLE
                recoverRate.visibility = View.INVISIBLE
                recoverLay.visibility = View.INVISIBLE
                moreInfo.setText(R.string.lessInfo)
                TransitionManager.beginDelayedTransition(
                    cardView, TransitionSet()
                        .addTransition(ChangeBounds())
                )
                val params: ViewGroup.LayoutParams = cardView.layoutParams
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                cardView.layoutParams = params
                recovery.visibility = View.VISIBLE
                totalInfoLay.visibility = View.VISIBLE
                recoverRate.visibility = View.VISIBLE
                recoverLay.visibility = View.VISIBLE
                expanded=true
            }else if(expanded){
                recovery.visibility = View.GONE
                totalInfoLay.visibility = View.GONE
                recoverRate.visibility = View.GONE
                recoverLay.visibility =View.GONE
                moreInfo.setText(R.string.moreInfo)
                TransitionManager.beginDelayedTransition(
                    cardView, TransitionSet()
                        .addTransition(ChangeBounds())
                )

                val params: ViewGroup.LayoutParams = cardView.layoutParams
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                cardView.layoutParams = params
                expanded=false
            }
        }

    }
}