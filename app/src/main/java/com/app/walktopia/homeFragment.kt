package com.app.walktopia

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import java.io.IOException
import java.util.*

class homeFragment : Fragment(), SensorEventListener {

    private var sensorManager: SensorManager? = null

    private var running = false
    private var total = 0f
    private var previous = 0f
    private lateinit var stepsTaken: TextView
    lateinit var progress: CircularProgressBar
    lateinit var address: TextView
    lateinit var city: TextView
    lateinit var calories: TextView
    lateinit var distance: TextView
    val ACTIVITY_RECOGNITION_REQUEST_CODE = 100
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val REQUEST_CODE = 100


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)



        stepsTaken = view.findViewById(R.id.stepsTaken)
        progress = view.findViewById(R.id.progress_circular)
        address = view.findViewById(R.id.address)
        city = view.findViewById(R.id.city)
        distance = view.findViewById(R.id.distance)
        calories = view.findViewById(R.id.calories)

        val sharedPreferences =
            requireActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE)
        val value = sharedPreferences.getString("steps", "0")
        stepsTaken.text = value
        val value2 = sharedPreferences.getString("distance", "0")
        distance.text = value2
        val value3 = sharedPreferences.getString("calories", "0")
        calories.text = value3

        progress.apply {
            setProgressWithAnimation(stepsTaken.text.toString().toFloat())
        }


        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        getLastLocation()

        if (isPermissionGranted()) {
            requestPermission()
        }


        loadData()
        resetSteps()

        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager





        return view
    }

    override fun onResume() {
        super.onResume()
        getLastLocation()
        running = true
        val stepSensor: Sensor? = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            Toast.makeText(
                requireContext(),
                "No sensor detected on this device",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {

        if (running) {
            total = event!!.values[0]
            val currentSteps = total.toInt() - previous.toInt()
            stepsTaken.text = ("$currentSteps")
            distance.text = ((stepsTaken.text.toString().toFloat() * 78) as Float / 100f).toString()
            calories.text = ((stepsTaken.text.toString().toFloat() * 0.045).toInt()).toString()

            val value = stepsTaken.text.toString().trim { it <= ' ' }
            val value2 = distance.text.toString().trim { it <= ' ' }
            val value3 = calories.text.toString().trim { it <= ' ' }
            val sharedPref =
                requireActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("steps", value)
            editor.putString("distance", value2)
            editor.putString("calories", value3)
            editor.apply()

            progress.apply {
                setProgressWithAnimation(currentSteps.toFloat())
            }

        }

    }

    private fun resetSteps() {
        stepsTaken.setOnClickListener {
            Toast.makeText(requireContext(), "Long tap to reset steps", Toast.LENGTH_SHORT).show()
        }

        stepsTaken.setOnLongClickListener {
            previous = total
            stepsTaken.text = 0.toString()
            distance.text = ((stepsTaken.text.toString().toFloat() * 78) as Float / 100f).toString()
            calories.text = ((stepsTaken.text.toString().toFloat() * 0.045).toInt()).toString()

            val value = stepsTaken.text.toString().trim { it <= ' ' }
            val value2 = distance.text.toString().trim { it <= ' ' }
            val value3 = calories.text.toString().trim { it <= ' ' }
            val sharedPref =
                requireActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("steps", value)
            editor.putString("distance", value2)
            editor.putString("calories", value3)
            editor.apply()
            saveData()

            true
        }
    }

    private fun saveData() {
        val sharedPref = requireActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putFloat("key1", previous)
        editor.apply()
    }

    private fun loadData() {
        val sharedPref = requireActivity().getSharedPreferences("myKey", Context.MODE_PRIVATE)
        val saved = sharedPref.getFloat("key1", 0f)
        Log.d("homeFragment", "$saved")
        previous = saved


    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.ACTIVITY_RECOGNITION
        ) != PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                ACTIVITY_RECOGNITION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()

            } else {
                Toast.makeText(
                    requireActivity(),
                    "Please provide the required permission",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ACTIVITY_RECOGNITION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {

                }
            }
        }
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        try {
                            val geocoder = Geocoder(requireActivity(), Locale.getDefault())
                            val addresses: List<Address>? =
                                geocoder.getFromLocation(location.latitude, location.longitude, 1)

                            address.text =
                                addresses!![0].subLocality + ", " + addresses[0].locality + ", " + addresses[0].adminArea

                            city.text = addresses[0].postalCode


                            val value = addresses[0].latitude.toString().trim { it <= ' ' }
                            val value2 = addresses[0].longitude.toString().trim { it <= ' ' }
                            val sharedPref = requireActivity().getSharedPreferences(
                                "myKey",
                                Context.MODE_PRIVATE
                            )
                            val editor = sharedPref.edit()
                            editor.putFloat("lat", value.toFloat())
                            editor.putFloat("lon", value2.toFloat())
                            editor.apply()

                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
        } else {
            askPermission()
        }
    }

    private fun askPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE
        )
    }


}