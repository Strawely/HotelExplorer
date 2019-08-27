package com.example.hotelexplorer.model

import android.graphics.Bitmap

data class Hotel (
    var id: Long,
    var name: String = "",
    var address: String = "",
    var stars: Float = 0f,
    var distance: Float = 0f,
    var suitesAvailability: String = "",
    var image: String?,
    var lat: Double?,
    var lon: Double?,
    @Transient
    var imageBmp: Bitmap?
){
    /**
     * Преобразовывает список доступных номеров из строки в объект Списка
     */
    fun getSuitesAvailable(): ArrayList<Int>{
        val suits = ArrayList<Int>()
        for (suit in suitesAvailability.split(":")){
            try {
                suits.add(suit.toInt())
            }catch (e: NumberFormatException){}
        }
        return suits
    }

    /**
     * Преобразовывает список доступных номеров в "красивый" формат
     */
    fun getFormattedSuitsAvailability(): String{
        return suitesAvailability.replace("\"", "")
            .trimEnd(':')
            .replace(":", ", ")
    }

    /**
     * ПРедставляет координаты отеля в виде строки
     */
    fun getFormattedCoordinates() = "%.6f".format(lat) + "; " + "%.6f".format(lon)
}