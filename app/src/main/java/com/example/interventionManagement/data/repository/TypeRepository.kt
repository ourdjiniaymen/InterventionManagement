package com.example.interventionManagement.data.repository


class TypeRepository {
    companion object {
        fun getTypes(): List<String> {
            val listTypes = ArrayList<String>()
            listTypes.add("Type1")
            listTypes.add("Type2")
            listTypes.add("Type3")
            listTypes.add("Type4")
            return listTypes
        }

    }
}