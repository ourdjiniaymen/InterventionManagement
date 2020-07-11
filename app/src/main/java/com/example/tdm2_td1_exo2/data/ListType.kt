package com.example.tdm2_td1_exo2.data


class ListType {
    companion object {
        fun getType(): List<String> {
            val listType = ArrayList<String>()
            listType.add("Type1")
            listType.add("Type2")
            listType.add("Type3")
            listType.add("Type4")
            return listType
        }

    }
}