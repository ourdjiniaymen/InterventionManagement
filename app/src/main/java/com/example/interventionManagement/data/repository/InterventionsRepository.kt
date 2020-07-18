package com.example.interventionManagement.data.repository


import com.example.interventionManagement.data.entity.Intervention
import com.example.interventionManagement.utility.FileReader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class InterventionsRepository {
    companion object {
        var listInterventions :ArrayList<Intervention> = arrayListOf()

        fun saveInterventions(){
            //convert list interventions to json
            val gson = Gson()
            val arrayInterventionType = object : TypeToken<ArrayList<Intervention>>() {}.type
            val jsonList = gson.toJson(listInterventions,arrayInterventionType)
            //write file
            val fileReader = FileReader()
            fileReader.writeFile(jsonList)
        }

        fun readInterventions(){
            val fileReader = FileReader()
            val fileContext = fileReader.readFile()
            val gson = Gson()
            val arrayInterventionType = object : TypeToken<ArrayList<Intervention>>() {}.type
            listInterventions = gson.fromJson(fileContext,arrayInterventionType)
        }
    }
}