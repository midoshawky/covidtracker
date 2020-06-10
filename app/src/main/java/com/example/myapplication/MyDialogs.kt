package com.example.myapplication

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog

class MyDialogs(val context: Context,val layoutInflater: LayoutInflater){
    fun showThanksDialog()
    {
        val inflater = layoutInflater
        val inflated = inflater.inflate(R.layout.thanksdialoglay,null)
        val closeBtn = inflated.findViewById<Button>(R.id.close)
        val dialog = AlertDialog.Builder(context)
        dialog.setView(inflated)
            .setCancelable(false)
        val setDialog = dialog.create()
        setDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setDialog.show()

        closeBtn.setOnClickListener {
            setDialog.dismiss()
        }
    }
    fun showNoConnectionDialog()
    {
        val mapViewTraker:MapViewTraker = MapViewTraker()
        val inflater = layoutInflater
        val inflated = inflater.inflate(R.layout.noconnection,null)
        val retryBtn = inflated.findViewById<Button>(R.id.retry)
        val closeApp = inflated.findViewById<TextView>(R.id.exit)
        val dialog = AlertDialog.Builder(context)
        dialog.setView(inflated)
            .setCancelable(false)
        val setDialog = dialog.create()
        setDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setDialog.show()

        retryBtn.setOnClickListener {
            try {
                if (available)
                    setDialog.dismiss()
                else
                    setDialog.show()
            }catch (e:Exception){

            }
        }
        closeApp.setOnClickListener {
            mapViewTraker.finish()
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun showSymptomsDialog(sympArr:ArrayList<String>)
    {
        val inflater = layoutInflater
        val inflated = inflater.inflate(R.layout.symtomps,null)
        val confirmBtn = inflated.findViewById<Button>(R.id.confirm)
        val resultTxt = inflated.findViewById<TextView>(R.id.result)
        val headache = inflated.findViewById<CheckBox>(R.id.headache)
        val fever = inflated.findViewById<CheckBox>(R.id.fvr)
        val cough = inflated.findViewById<CheckBox>(R.id.chg)
        val hardBreath = inflated.findViewById<CheckBox>(R.id.hb)
        val throat = inflated.findViewById<CheckBox>(R.id.throat)
        val dialog = AlertDialog.Builder(context)
        dialog.setView(inflated)
            .setCancelable(false)
        val setDialog = dialog.create()
        setDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setDialog.show()

        confirmBtn.setOnClickListener {
            headache.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked)
                    sympArr.add("Headache")
                else if(!isChecked)
                    sympArr.remove("Headache")
            }
            fever.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked)
                    sympArr.add("Fever")
                else if(!isChecked)
                    sympArr.remove("Fever")
            }
            cough.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked)
                    sympArr.add("Cough")
                else if(!isChecked)
                    sympArr.remove("Cough")
            }
            hardBreath.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked)
                    sympArr.add("Hard Breathing")
                else if(!isChecked)
                    sympArr.remove("Hard Breathing")
            }
            throat.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked)
                    sympArr.add("Sore Throat")
                else if(!isChecked)
                    sympArr.remove("Sore Throat")
            }
            setDialog.dismiss()
        }
    }
}