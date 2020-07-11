package com.example.tdm2_td1_exo2.entite

class Intervention (
    var id : Int,
    var date : String,
    var plombier : String,
    var type : String

) : Comparable<Intervention>{
    override fun compareTo(other: Intervention) = (id - other.id).toInt()
}