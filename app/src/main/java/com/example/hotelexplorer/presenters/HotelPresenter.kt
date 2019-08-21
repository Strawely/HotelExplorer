package com.example.hotelexplorer.presenters

import android.graphics.Bitmap
import com.example.hotelexplorer.Connector
import com.example.hotelexplorer.ImageProcessor
import com.example.hotelexplorer.model.Hotel
import com.example.hotelexplorer.views.IHotelView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HotelPresenter(var view: IHotelView, hotelId: Long) {
    var hotel: Hotel? = null
        private set

    init {
        if (hotelId != -1L) {
            loadHotel(hotelId)
        }
    }

    private fun loadHotel(hotelId: Long) {
        GlobalScope.launch {
            hotel = Connector.getSingleHotel(hotelId)
            if (hotel != null){
                withContext(Dispatchers.Main) {
                    view.fillTextViews(hotel ?: return@withContext)
                }
                val hotelImage = loadHotelImage()
                withContext(Dispatchers.Main){
                    view.initImage(ImageProcessor.reduceEdge(hotelImage))
                }
            }
            println("Hotel is null")
        }
    }

    private suspend fun loadHotelImage() = Connector.getImage(hotel?.image ?: "")
}