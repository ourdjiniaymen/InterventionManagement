package com.example.tdm2_td1_exo2

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.tdm2_td1_exo2.data.ListIntervention.Companion.readIntervention
import com.example.tdm2_td1_exo2.data.ListIntervention.Companion.sauvegarderIntervention
import com.example.tdm2_td1_exo2.ui.ListInterventionFragment


class MainActivity : AppCompatActivity() {
    companion object{
        fun replaceFragment (activity: FragmentActivity,layout : Int,fragment: Fragment){
            val fragmentTransaction = activity.supportFragmentManager?.beginTransaction()
            fragmentTransaction.replace(layout , fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        const val WRITE_REQUEST_CODE = 100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(this,R.id.navigation_fragment,ListInterventionFragment())
        //Request for permession at runtime
        val permissions =
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, WRITE_REQUEST_CODE)
        }
        //read list intervention from file
        if(checkPermessuin(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            readIntervention ()
        }
    }
    private fun checkPermessuin(permission: String)  : Boolean{
        val check = ContextCompat.checkSelfPermission(this,permission)
        return (check == PackageManager.PERMISSION_GRANTED)
    }

    override fun onPause() {
        sauvegarderIntervention()
        super.onPause()
    }

}
