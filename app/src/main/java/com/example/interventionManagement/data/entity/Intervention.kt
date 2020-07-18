package com.example.interventionManagement.data.entity

data class Intervention (
    var id : Int,
    var date : String,
    var plumber : String,
    var type : String

) : Comparable<Intervention>{
    override fun compareTo(other: Intervention) = (id - other.id)
}