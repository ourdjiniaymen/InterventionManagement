package com.example.tdm2_td1_exo2.data

import android.widget.Toast
import com.example.tdm2_td1_exo2.MainActivity
import com.example.tdm2_td1_exo2.entite.Intervention
import com.example.tdm2_td1_exo2.file.FileReader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.content.Context
class ListIntervention {
    companion object {
        var listIntervention :ArrayList<Intervention> = arrayListOf()
        fun sauvegarderIntervention(){
            //convert list comments to json
            val gson = Gson()
            val arrayInterventionType = object : TypeToken<ArrayList<Intervention>>() {}.type
            val jsonList = gson.toJson(listIntervention,arrayInterventionType)
            //write file
            val fileReader = FileReader()
            fileReader.writeFile(jsonList)
        }

        fun readIntervention (){
            val fileReader = FileReader()
            val fileContext = fileReader.readFile()
            val gson = Gson()
            val arrayInterventionType = object : TypeToken<ArrayList<Intervention>>() {}.type
            listIntervention = gson.fromJson(fileContext,arrayInterventionType)
        }


    }
}