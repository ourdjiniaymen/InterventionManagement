package com.example.tdm2_td1_exo2.file

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.tdm2_td1_exo2.MainActivity
import kotlinx.coroutines.newSingleThreadContext
import java.io.*


class FileReader (){
    private val fileName: String = "list_intervention.json"
    fun writeFile(text : String){
        if(isExternalStorageWritable()){
            val file = File(Environment.getExternalStorageDirectory(),fileName)
            try {
                val fos = FileOutputStream(file)
                fos.write(text.toByteArray())
                fos.close()
            }catch (e :IOException){
                e.printStackTrace()
            }
        }
    }

    fun readFile() : String{
        val  sb = StringBuilder()
        if(isExternalStorageReadable()){
            val file = File(Environment.getExternalStorageDirectory(),fileName)
            try {
                val fis = FileInputStream(file)
                val isr = InputStreamReader(fis)
                val buff = BufferedReader(isr)
                var line : String? = null
                line = buff.readLine()
                while (line  != null){
                    sb.append(line+ "\n")
                    line = buff.readLine()
                }
                fis.close()
            }catch (e :IOException){
               e.printStackTrace()
            }
        }
        return sb.toString()
    }

    private fun isExternalStorageWritable() : Boolean{
        if(Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()){
            return true
        }
        return false
    }
    fun isExternalStorageReadable() : Boolean {
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || Environment.MEDIA_MOUNTED_READ_ONLY == Environment.getExternalStorageState()) {
            return true
        }
        return false
    }
}
