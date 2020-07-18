package com.example.interventionManagement.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.interventionManagement.R
import com.example.interventionManagement.data.repository.InterventionsRepository.Companion.readInterventions
import com.example.interventionManagement.data.repository.InterventionsRepository.Companion.saveInterventions


class MainActivity : AppCompatActivity() {
    companion object {
        const val WRITE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Request for permession at runtime
        requestForPermission()

        //read list intervention from file
        if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            readInterventions()
        }
    }


    private fun checkPermission(permission: String): Boolean {
        val check = ContextCompat.checkSelfPermission(this, permission)
        return (check == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestForPermission() {
        val permissions =
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, WRITE_REQUEST_CODE)
        }
    }

    override fun onPause() {
        saveInterventions()
        super.onPause()
    }

}
