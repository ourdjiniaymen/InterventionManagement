package com.example.interventionManagement.utility

import android.os.Environment
import com.example.interventionManagement.R
import java.io.*


class FileReader() {

    private val directoryName = R.string.app_name.toString()
    private val fileName: String = "list_intervention.json"


    fun writeFile(text: String) {
        if (isExternalStorageWritable()) {
            val file = File(Environment.getExternalStorageDirectory(), fileName)
            try {
                val fos = FileOutputStream(file)
                fos.write(text.toByteArray())
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun readFile(): String {
        val sb = StringBuilder()
        if (isExternalStorageReadable()) {
            val file = File(Environment.getExternalStorageDirectory(), fileName)
            try {
                val fis = FileInputStream(file)
                val isr = InputStreamReader(fis)
                val buff = BufferedReader(isr)
                var line: String? = null
                line = buff.readLine()
                while (line != null) {
                    sb.append(line + "\n")
                    line = buff.readLine()
                }
                fis.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return sb.toString()
    }

    private fun isExternalStorageWritable(): Boolean {
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            return true
        }
        return false
    }

    private fun isExternalStorageReadable(): Boolean {
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || Environment.MEDIA_MOUNTED_READ_ONLY == Environment.getExternalStorageState()) {
            return true
        }
        return false
    }
}
