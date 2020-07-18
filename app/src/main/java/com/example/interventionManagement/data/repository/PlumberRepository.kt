package com.example.interventionManagement.data.repository


class PlumberRepository {
    companion object {
        fun getPlumbers(): List<String> {
            val listPlumbers = ArrayList<String>()
            listPlumbers.add("Plombier1")
            listPlumbers.add("Plombier2")
            listPlumbers.add("Plombier3")
            listPlumbers.add("Plombier4")
            return listPlumbers
        }


    }
}