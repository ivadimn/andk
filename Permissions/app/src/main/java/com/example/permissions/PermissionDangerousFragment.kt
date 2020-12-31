package com.example.permissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices

class PermissionDangerousFragment : Fragment(R.layout.fragment_dangerous_permission) {

    private lateinit var locationTextView: TextView
    private var rationaleDialog : AlertDialog? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        locationTextView = requireView().findViewById(R.id.locationTextView)

        requireView().findViewById<Button>(R.id.getLocation).setOnClickListener {
            showCurrentLocationWithPermissionCheck()
        }

    }

    private fun showCurrentLocationWithPermissionCheck() {
        val isLocationPermissionGranted = ActivityCompat.checkSelfPermission(requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (isLocationPermissionGranted) {
            showLocationInfo()
        }
        else {
            val needRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (needRationale) {
                showLocationRationaleDialog()
            }
            else {
                requestLocationPermission()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            showLocationInfo()
        }
        else {
            val needRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (needRationale) {
                showLocationRationaleDialog()
            }
        }
    }

    private fun showLocationRationaleDialog() {
        rationaleDialog = AlertDialog.Builder(requireContext())
            .setMessage("Необходимр пазрешение для отображения информации по локации")
            .setPositiveButton("Ok", {_, _ -> requestLocationPermission()})
            .setNegativeButton("Отмена", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rationaleDialog?.dismiss()
        rationaleDialog = null
    }

    private fun showLocationInfo() {
        
        LocationServices.getFusedLocationProviderClient(requireContext())
            .lastLocation
            .addOnSuccessListener {
                it?.let {
                    locationTextView.text = """
                        Lat = ${it.latitude}
                        Lng = ${it.longitude}
                        Alt = ${it.altitude}
                        Speed = ${it.speed}
                        Accuracy = ${it.accuracy}
                    """.trimIndent()
                } ?: toast("Локация отсутствует")
            }
            .addOnCanceledListener {
                toast("Location request has been canceled")
            }
            .addOnFailureListener {
                toast("Location request failed")
            }


    }

    private fun requestLocationPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_CODE)
    }


    companion object {
        private const val PERMISSION_REQUEST_CODE = 101
    }

    private fun toast(msg : String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}