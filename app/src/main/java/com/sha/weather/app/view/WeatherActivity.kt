package com.sha.weather.app.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.sha.weather.app.R

/**
 * Created by Shahul Hameed on 13/11/2024.
 */
class WeatherActivity: AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        requestLocationPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Permission denied
                showPermissionDeniedMessage()
            } else {
                // Permission denied permanently, show a message and provide an option to open settings
                showPermissionDeniedPermanentlyMessage()
            }
        }
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), Companion.LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location:   Location? ->
                    if (location != null) {
                        sendLatLongToOpenWeather(location.latitude, location.longitude)
                    }
                }
        }
    }

    private fun sendLatLongToOpenWeather(latitude: Double, longitude: Double) {
        val bundle = Bundle().apply {
            putDouble("latitude", latitude)
            putDouble("longitude", longitude)
        }
        findNavController(R.id.nav_host_fragment).navigate(R.id.weatherFragment, bundle)
    }

    private fun showPermissionDeniedMessage() {
        Toast.makeText(this, "Location permission is required to access location features.", Toast.LENGTH_LONG).show()
    }

    private fun showPermissionDeniedPermanentlyMessage() {
        Toast.makeText(this, "Location permission was denied permanently. Please enable it in settings.", Toast.LENGTH_LONG).show()
        // Todo: We can provide a button to open the app settings
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE =  1000
    }
}