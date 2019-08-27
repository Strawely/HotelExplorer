package com.example.hotelexplorer.presenters

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.hotelexplorer.Connector
import com.example.hotelexplorer.ImageProcessor
import com.example.hotelexplorer.Repository
import com.example.hotelexplorer.views.IHotelView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HotelPresenter(private var view: IHotelView, private val hotelId: Long): LifecycleObserver {

    private fun loadHotel() {
        GlobalScope.launch {
            Repository.currentHotel = Connector.getSingleHotel(hotelId)

            if (Repository.currentHotel != null){
                withContext(Dispatchers.Main) {
                    view.fillTextViews(Repository.currentHotel ?: return@withContext)
                }

                Repository.currentHotel?.imageBmp = loadHotelImage()

                withContext(Dispatchers.Main){
                    view.fillImageView(ImageProcessor.reduceEdge(Repository.currentHotel?.imageBmp))
                }
            }
            println("Hotel is null")
        }
    }

    private suspend fun loadHotelImage() = Connector.getImage(Repository.currentHotel?.image ?: "")

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(){
        if(Repository.currentHotel == null){
            loadHotel()
        } else {
            view.fillTextViews(Repository.currentHotel ?: return)
            view.fillImageView(ImageProcessor.reduceEdge(Repository.currentHotel?.imageBmp))
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        Repository.currentHotel = null
    }
}