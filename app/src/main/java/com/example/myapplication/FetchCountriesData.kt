package com.example.myapplication

import android.R
import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.lang.NullPointerException

class FetchCountriesData(val recylceView:RecyclerView,val countriesDataLst:ArrayList<CountriesData>,context: Context) : AsyncTask<String, String, ArrayList<CountriesData>>() {
    val client = OkHttpClient()
    var countries:ArrayList<String> = ArrayList()
    lateinit var request:Request
    val mapViewTraker = MapViewTraker()
    var dataLoaded:Boolean = false
    override fun doInBackground(vararg params: String?): ArrayList<CountriesData>? {
        request = Request.Builder()
            .url("https://api.covid19api.com/summary")
            .get()
            .build()
        val response = client.newCall(request).execute()
        try {
                if (response.isSuccessful) {
                    val body = response.body()!!.string()
                    val jsonObject = JSONObject(body)
                    val global = jsonObject.getJSONObject("Global")
                    val countriesDta = jsonObject.getJSONArray("Countries")
                    //countries.clear()
                    for (index in 0.until(countriesDta.length())) {
                        val countryName: String =
                            countriesDta.getJSONObject(index).getString("Country")
                        val newConfirmed: String =
                            countriesDta.getJSONObject(index).getString("NewConfirmed")
                        val newRecovered: String =
                            countriesDta.getJSONObject(index).getString("NewRecovered")
                        val newDead: String =
                            countriesDta.getJSONObject(index).getString("NewDeaths")
                        val totalConfirmed: String =
                            countriesDta.getJSONObject(index).getString("TotalConfirmed")
                        val totalRecovered: String =
                            countriesDta.getJSONObject(index).getString("TotalRecovered")
                        val totalDead: String =
                            countriesDta.getJSONObject(index).getString("TotalDeaths")
                        println("Country : $countryName , Confirmed : $newConfirmed")
                        countriesDataLst.add(
                            CountriesData(
                                countryName, newConfirmed, newRecovered, newDead
                                , totalConfirmed, totalRecovered, totalDead
                            )
                        )
                        countries.add(countryName)
                    }
                    dataLoaded = true
                }else{
                    dataLoaded = false
                }
            return countriesDataLst
        }catch (e:java.lang.Exception){}
        return countriesDataLst
    }
    override fun onPostExecute(result: ArrayList<CountriesData>?) {
        if(result!=null){
            super.onPostExecute(result)
            recylceView.adapter = RVAdapter(mapViewTraker,result)
            recylceView.adapter?.notifyDataSetChanged()
            println("Data load")
        }
    }
}