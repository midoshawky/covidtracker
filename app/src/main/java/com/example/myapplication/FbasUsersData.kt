package com.example.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class FbasUsersData {
    var myRef = FirebaseDatabase.getInstance().getReference("People")
    private var isDone: Boolean = true
    fun fetchData(mapView: GoogleMap, context: Context) {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    try {
                        for (key in snapshot.children) {
                            val long = key.child("Longitude").getValue(true).toString()
                            val lat = key.child("Latitude").getValue(true).toString()
                            val msg = key.child("Message").getValue(true).toString()
                            val hlthstate = key.child("State").getValue(true).toString()

                            var stateMark: BitmapDescriptor? = null
                            if (hlthstate == "Safe")
                                stateMark = bitmapDescriptorFromVector(context, R.drawable.ic_safe)
                            else if (hlthstate == "Critical")
                                stateMark =
                                    bitmapDescriptorFromVector(context, R.drawable.ic_critical)
                            println("full data : $long, $lat , $msg")
                            mapView.addMarker(
                                MarkerOptions()
                                    .position(LatLng(lat.toDouble(), long.toDouble()))
                                    .title("$msg")
                                    .icon(stateMark)
                            )
                        }
                    } catch (e: Exception) {

                    }

                }
                mapView.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
                    override fun onMarkerClick(marker: Marker?): Boolean {
                        mapView.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    marker!!.position.latitude,
                                    marker.position.longitude
                                ), 20f
                            )
                        )
                        marker.showInfoWindow()
                        return true
                    }
                })
            }
        })
    }

    fun markLocation(lat: String, long: String, msg: String, state: String): Boolean {
        var unformatted: String = "$lat$long"
        val key = unformatted.replace(".", "")
        isDone = try {
            myRef.child(key).child("Latitude").setValue(lat)
            myRef.child(key).child("Longitude").setValue(long)
            myRef.child(key).child("Message").setValue(msg)
            myRef.child(key).child("State").setValue(state)
            true
        } catch (e: Exception) {
            println(e.message)
            false
        }
        return isDone
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}