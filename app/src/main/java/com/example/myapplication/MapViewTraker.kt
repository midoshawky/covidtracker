package com.example.myapplication

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_map_view_traker.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import kotlin.math.abs


class MapViewTraker : AppCompatActivity(), OnMapReadyCallback {
    var dataShow: RecyclerView? = null
    var autoCompleteText: AutoCompleteTextView? = null
    val client = OkHttpClient()
    var countriesDataLst: ArrayList<CountriesData>? = null
    val usersData: FbasUsersData = FbasUsersData()
    var rvAdapter: RVAdapter? = null
    var googleMap: GoogleMap? = null
    var Tstbtn: FloatingActionButton? = null
    var emrgencyBtn: Button? = null
    var safetyValu: String = "Safe"
    var phone: String = ""
    var messageTxt: EditText? = null
    var MessageTxt: String = ""
    var syptomes: ArrayList<String>? = null
    var myLat: Double? = null
    var myLong: Double? = null
    var loaded: Boolean? = false
    var autoCompleteArrayAdapter: ArrayAdapter<String>? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    val countries = arrayListOf(
        "Afghanistan",
        "Albania",
        "Algeria",
        "Andorra",
        "Angola",
        "Anguilla",
        "Antigua &amp; Barbuda",
        "Argentina",
        "Armenia",
        "Aruba",
        "Australia",
        "Austria",
        "Azerbaijan",
        "Bahamas",
        "Bahrain",
        "Bangladesh",
        "Barbados",
        "Belarus",
        "Belgium",
        "Belize",
        "Benin",
        "Bermuda",
        "Bhutan",
        "Bolivia",
        "Bosnia &amp; Herzegovina",
        "Botswana",
        "Brazil",
        "British Virgin Islands",
        "Brunei",
        "Bulgaria",
        "Burkina Faso",
        "Burundi",
        "Cambodia",
        "Cameroon",
        "Cape Verde",
        "Cayman Islands",
        "Chad",
        "Chile",
        "China",
        "Colombia",
        "Congo",
        "Cook Islands",
        "Costa Rica",
        "Cote D Ivoire",
        "Croatia",
        "Cruise Ship",
        "Cuba",
        "Cyprus",
        "Czech Republic",
        "Denmark",
        "Djibouti",
        "Dominica",
        "Dominican Republic",
        "Ecuador",
        "Egypt",
        "El Salvador",
        "Equatorial Guinea",
        "Estonia",
        "Ethiopia",
        "Falkland Islands",
        "Faroe Islands",
        "Fiji",
        "Finland",
        "France",
        "French Polynesia",
        "French West Indies",
        "Gabon",
        "Gambia",
        "Georgia",
        "Germany",
        "Ghana",
        "Gibraltar",
        "Greece",
        "Greenland",
        "Grenada",
        "Guam",
        "Guatemala",
        "Guernsey",
        "Guinea",
        "Guinea Bissau",
        "Guyana",
        "Haiti",
        "Honduras",
        "Hong Kong",
        "Hungary",
        "Iceland",
        "India",
        "Indonesia",
        "Iran",
        "Iraq",
        "Ireland",
        "Isle of Man",
        "Israel",
        "Italy",
        "Jamaica",
        "Japan",
        "Jersey",
        "Jordan",
        "Kazakhstan",
        "Kenya",
        "Kuwait",
        "Kyrgyz Republic",
        "Laos",
        "Latvia",
        "Lebanon",
        "Lesotho",
        "Liberia",
        "Libya",
        "Liechtenstein",
        "Lithuania",
        "Luxembourg",
        "Macau",
        "Macedonia",
        "Madagascar",
        "Malawi",
        "Malaysia",
        "Maldives",
        "Mali",
        "Malta",
        "Mauritania",
        "Mauritius",
        "Mexico",
        "Moldova",
        "Monaco",
        "Mongolia",
        "Montenegro",
        "Montserrat",
        "Morocco",
        "Mozambique",
        "Namibia",
        "Nepal",
        "Netherlands",
        "Netherlands Antilles",
        "New Caledonia",
        "New Zealand",
        "Nicaragua",
        "Niger",
        "Nigeria",
        "Norway",
        "Oman",
        "Pakistan",
        "Palestine",
        "Panama",
        "Papua New Guinea",
        "Paraguay",
        "Peru",
        "Philippines",
        "Poland",
        "Portugal",
        "Puerto Rico",
        "Qatar",
        "Reunion",
        "Romania",
        "Russia",
        "Rwanda",
        "Saint Pierre &amp; Miquelon",
        "Samoa",
        "San Marino",
        "Satellite",
        "Saudi Arabia",
        "Senegal",
        "Serbia",
        "Seychelles",
        "Sierra Leone",
        "Singapore",
        "Slovakia",
        "Slovenia",
        "South Africa",
        "South Korea",
        "Spain",
        "Sri Lanka",
        "St Kitts &amp; Nevis",
        "St Lucia",
        "St Vincent",
        "St. Lucia",
        "Sudan",
        "Suriname",
        "Swaziland",
        "Sweden",
        "Switzerland",
        "Syria",
        "Taiwan",
        "Tajikistan",
        "Tanzania",
        "Thailand",
        "Timor L'Este",
        "Togo",
        "Tonga",
        "Trinidad &amp; Tobago",
        "Tunisia",
        "Turkey",
        "Turkmenistan",
        "Turks &amp; Caicos",
        "Uganda",
        "Ukraine",
        "United Arab Emirates",
        "United Kingdom",
        "Uruguay",
        "Uzbekistan",
        "Venezuela",
        "Vietnam",
        "Virgin Islands (US)",
        "Yemen",
        "Zambia",
        "Zimbabwe"
    )
    val emergencyNumbers: Map<String, String> = mapOf(
        Pair("Egypt", "105"),
        Pair("Algeria", "3030"),
        Pair("Libya", "191"),
        Pair("Morocco,", "+212 0801004747"),
        Pair("Sudan", "9090"),
        Pair("Tunisia", "190")
    )
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var selectedCountry: String
    lateinit var geocoder: Geocoder
    var myDialogs: MyDialogs ?= null
    lateinit var loctioanClinet: FusedLocationProviderClient
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_view_traker)
        val mapFragment: SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        val Btoombtn = findViewById<Button>(R.id.btnBottom)
        val bottomSheet = findViewById<View>(R.id.bottomSheet)
        val bottomSheetbehavior: BottomSheetBehavior<View> = BottomSheetBehavior.from(bottomSheet)
        val markLocation = findViewById<Button>(R.id.send)
        val safeRB = findViewById<RadioButton>(R.id.safe)
        val criticalRB = findViewById<RadioButton>(R.id.critical)
        val youState = findViewById<RadioGroup>(R.id.currstate)

        mapFragment.getMapAsync(this)

        coordinatorLayout = CoordinatorLayout(this)
        Tstbtn = findViewById(R.id.tstbtn)
        emrgencyBtn = findViewById(R.id.emrg)
        dataShow = findViewById(R.id.dataRV)
        messageTxt = findViewById(R.id.describ)
        autoCompleteText = findViewById(R.id.searchView)
        dataShow!!.isNestedScrollingEnabled = true
        geocoder = Geocoder(this)
        loctioanClinet = LocationServices.getFusedLocationProviderClient(this)
        myDialogs = MyDialogs(this, layoutInflater)
        countriesDataLst = ArrayList()
        syptomes = ArrayList()
        //val dark: TransitionDrawable = maincontainer.background as TransitionDrawable
        val connectionStatus = ConnectionStatus(this, layoutInflater)
        println("Stats ${connectionStatus.checkNetworkAvailability()}")
        dataFetching()

        autoCompleteArrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, countries
        )

        autoCompleteText!!.setAdapter(autoCompleteArrayAdapter)
        bottomSheetbehavior.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {

            }

            override fun onStateChanged(sheet: View, state: Int) {
                when (state) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        Thread.sleep(500)
                        getSavedLocation()
                    }
                }
            }

        })

        Btoombtn.setOnClickListener {
            if (bottomSheetbehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetbehavior.state = BottomSheetBehavior.STATE_EXPANDED
                bottomSheetbehavior.state = BottomSheetBehavior.STATE_SETTLING
                Btoombtn.text = resources.getText(R.string.Hide)
                Handler().postDelayed({ getSavedLocation() }, 600)
            } else {
                bottomSheetbehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                Btoombtn.text = resources.getText(R.string.markLoctiaon)
            }
        }

        autoCompleteText!!.setOnItemClickListener { parent, view, p, id ->
            selectedCountry = autoCompleteText!!.text.toString()
            Toast.makeText(this, "Searching for ${autoCompleteText!!.text}", Toast.LENGTH_LONG)
                .show()
        }
        youState.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.safe -> safetyValu = "Safe"
                R.id.critical -> {
                    myDialogs!!.showSymptomsDialog(syptomes!!)
                    if (syptomes!!.size in 1..5)
                        safetyValu = "Critical"
                    else
                        safetyValu = "Safe"
                }
            }
        }
        markLocation.setOnClickListener {
            MessageTxt = messageTxt!!.text.toString()
            try {
                if (MessageTxt.isNotEmpty()) {
                    if (usersData.markLocation(
                            myLat.toString(),
                            myLong.toString(),
                            MessageTxt,
                            safetyValu
                        )
                    ) {
                        showMsg("Done", resources.getColor(R.color.scndColor))
                    } else {
                        showMsg("Something wrong", resources.getColor(R.color.frstColor))
                    }
                } else
                    showMsg(
                        "Please provide some information",
                        resources.getColor(R.color.frstColor)
                    )
            } catch (e: Exception) {
                println(e.message)
            }

        }
        emrgencyBtn!!.setOnClickListener {
            if (phone.isNotEmpty())
                emergencyCall(phone)
            else
                showMsg(
                    "may be this feature is not avalible right now",
                    resources.getColor(R.color.frstColor)
                )
        }

        Handler().postDelayed({
            //dark.startTransition(500)
            val myDialog: MyDialogs = MyDialogs(this, layoutInflater)
            myDialog.showThanksDialog()
        }, 6000)
    }

    fun dataFetching() {
        try {
            for (i in 0..5) {
                println(i)
            }
            val request = Request.Builder()
                .url("https://api.covid19api.com/summary")
                .get()
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful)
                        call.request()
                    else {
                        val body = response.body()!!.string()
                        val jsonObject = JSONObject(body)
                        val global = jsonObject.getJSONObject("Global")
                        val countriesDta = jsonObject.getJSONArray("Countries")
                        countries.clear()
                        countriesDataLst = ArrayList<CountriesData>()
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
                            countriesDataLst!!.add(
                                CountriesData(
                                    countryName, newConfirmed, newRecovered, newDead
                                    , totalConfirmed, totalRecovered, totalDead
                                )
                            )
                            countries.add(countryName)
                        }
                        loaded = true
                        this@MapViewTraker.runOnUiThread {
                            rvAdapter = RVAdapter(this@MapViewTraker, countriesDataLst)
                            layoutManager = LinearLayoutManager(this@MapViewTraker)
                            dataShow!!.layoutManager = layoutManager
                            dataShow!!.adapter = rvAdapter
                            autoCompleteArrayAdapter = ArrayAdapter<String>(
                                this@MapViewTraker,
                                android.R.layout.simple_list_item_1,
                                countries
                            )
                            autoCompleteText!!.setAdapter(autoCompleteArrayAdapter)
                            if (loaded!!) {
                                println("data loaded : ${dataFetching()}")
                                dataload.visibility = View.GONE
                            } else {
                                println("data loaded : ${dataFetching()}")
                                dataload.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            })

        } catch (e: Exception) {

        }
    }

    private fun scrollToPlace(selectedCountry: String) {
        val smoothScroller: RecyclerView.SmoothScroller = object : LinearSmoothScroller(this) {
            override fun calculateTimeForScrolling(dx: Int): Int {
                return 500
            }

            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = countries.indexOf(selectedCountry)
        dataShow!!.layoutManager!!.startSmoothScroll(smoothScroller)
    }

    override fun onMapReady(mapView: GoogleMap?) {
        val firstLoctaion = LatLng(3.3155589, 66.4856364)
        mapView!!.moveCamera(CameraUpdateFactory.newLatLng(firstLoctaion))
        usersData.fetchData(mapView, this)
        Tstbtn!!.setOnClickListener {
            if (selectedCountry.isNotEmpty()) {
                scrollToPlace(selectedCountry)
                runOnUiThread {
                    goToPlace(mapView, selectedCountry)
                }
            } else showMsg("Please choose a place first", resources.getColor(R.color.frstColor))
        }
    }

    fun goToPlace(googleMap: GoogleMap, selectedCountry: String) {
        try {
            val location = geocoder.getFromLocationName(selectedCountry, 1)
            val newLocation = LatLng(location[0].latitude, location[0].longitude)
            val spreadrate =
                (countriesDataLst!![countries.indexOf(selectedCountry)].totalCnfrm.toDouble())

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 5.5f))
            googleMap.addCircle(
                CircleOptions()
                    .center(newLocation)
                    .strokeWidth(0f)
                    .fillColor(resources.getColor(R.color.transparentRed))
                    .radius(spreadrate * 15)
            )
        } catch (e: java.lang.Exception) {
        }

    }


    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            1
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun getMyLocation() {
        showMsg("Please wait detecting your location", resources.getColor(R.color.scndColor))
        val locRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(0)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setFastestInterval(0)
            .setNumUpdates(1)
        val locCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                showMsg("Location detected successful", resources.getColor(R.color.scndColor))
                myLong = locationResult!!.lastLocation.longitude
                myLat = locationResult.lastLocation.latitude
                val country = geocoder.getFromLocation(myLat!!, myLong!!, 1)
                val countryName = country[0].countryName
                val phone: String = emergencyNumbers[countryName].toString()
                emrgencyBtn!!.text = "Emergency Call $countryName $phone"
            }
        }
        loctioanClinet.requestLocationUpdates(locRequest, locCallback, Looper.myLooper())
    }

    private fun getSavedLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                loctioanClinet.lastLocation.addOnCompleteListener(this) { task ->
                    val result: Location? = task.result
                    if (result == null) {
                        getMyLocation()
                    } else {
                        showMsg(
                            "Location detected successfully from last known location",
                            resources.getColor(R.color.scndColor)
                        )
                        myLong = result.longitude
                        myLat = result.latitude
                        val country = geocoder.getFromLocation(myLat!!, myLong!!, 1)
                        val countryName = country[0].countryName
                        phone = emergencyNumbers[countryName].toString()
                        var buttonTxt = "Emergency Call $countryName $phone"
                        if (emergencyNumbers.containsKey(countryName))
                            emrgencyBtn!!.text = buttonTxt
                        else {
                            buttonTxt = "Emergency Call is not available in your country"
                            emrgencyBtn!!.text = buttonTxt
                            emrgencyBtn!!.isActivated = false
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    fun showMsg(myMsg: String, color: Int) {
        val toast: Toast = Toast.makeText(this, myMsg, Toast.LENGTH_LONG)
        val view: View = toast.view
        view.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        val text: TextView = view.findViewById(android.R.id.message);
        text.setTextColor(resources.getColor(R.color.white))
        toast.show()
    }

    private fun emergencyCall(phoneNumber: String) {
        val phoneCallIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"));
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startActivity(phoneCallIntent)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.CALL_PHONE
                ),
                2
            )
        }
    }

}
