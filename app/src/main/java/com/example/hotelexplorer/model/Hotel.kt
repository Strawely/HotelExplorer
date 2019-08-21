package com.example.hotelexplorer.model

data class Hotel (
    var id: Long,
    var name: String = "",
    var address: String = "",
    var stars: Float = 0f,
    var distance: Float = 0f,
    var suitesAvailability: String = "",
    var image: String?,
    var lat: Double?,
    var lon: Double?
){
    fun getSuitesAvailable(): ArrayList<Int>{
        val suits = ArrayList<Int>()
        for (suit in suitesAvailability.split(":")){
            try {
                suits.add(suit.toInt())
            }catch (e: NumberFormatException){}
        }
        return suits
    }

    fun getFormattedSuitsAvailability(): String{
        return suitesAvailability.replace("\"", "")
            .trimEnd(':')
            .replace(":", ", ")
    }

    fun getFormattedCoordinates() = "%.6f".format(lat) + "; " + "%.6f".format(lon)
}