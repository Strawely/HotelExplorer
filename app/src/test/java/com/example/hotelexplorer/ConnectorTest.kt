package com.example.hotelexplorer

import org.junit.Test

class ConnectorTest {
    @Test
    fun testHotelsList(){
        val hotels = Connector.getHotels()
        hotels.forEach { println(it) }
    }

    @Test
    fun testSingleHotel(){
        val hotel = Connector.getSingleHotel(13370)
        println(hotel)
        println(hotel!!.getSuitesAvailable())
    }
}