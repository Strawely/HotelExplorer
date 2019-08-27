package com.example.hotelexplorer

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

class ConnectorTest {
    @Test
    fun testHotelsList(){
        GlobalScope.launch {
            val hotels = Connector.getHotels()
            hotels.forEach { assert(it.id > -1) }
        }
    }

    @Test
    fun testSingleHotel(){
        GlobalScope.launch {
            val hotel = Connector.getSingleHotel(13370)
            println(hotel)
            println(hotel!!.getSuitesAvailable())
        }
    }
}