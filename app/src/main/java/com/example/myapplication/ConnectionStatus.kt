package com.example.myapplication

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.os.Build
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import java.net.InetAddress
var available:Boolean = false
class ConnectionStatus(val context:Context,val layoutInflater: LayoutInflater) {
        @RequiresApi(Build.VERSION_CODES.M)
        fun checkNetworkAvailability() : Boolean{
            val myDialogs:MyDialogs = MyDialogs(context,layoutInflater)
            val mapViewTraker:MapViewTraker = MapViewTraker()
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                cm.registerDefaultNetworkCallback(object :ConnectivityManager.NetworkCallback(){
                    override fun onAvailable(network: Network) {
                        Toast.makeText(context,"Connected",Toast.LENGTH_LONG).show()
                        available = true
                        println(available)
                    }

                    override fun onUnavailable() {
                        Toast.makeText(context,"Not Connected",Toast.LENGTH_LONG).show()
                        available = false
                        println(available)
                        myDialogs.showNoConnectionDialog()
                    }

                    override fun onLost(network: Network) {
                        Toast.makeText(context,"Not Connected",Toast.LENGTH_LONG).show()
                        available = false
                        println(available)
                        myDialogs.showNoConnectionDialog()
                    }

                })
            }
            return available
        }

        fun isThereIsInternet() : Boolean{
            return try{
                val ipRequest:InetAddress = InetAddress.getByName("www.google.com")
                !ipRequest.equals("")
            }catch (e:Exception){
                false
            }
        }
    }
