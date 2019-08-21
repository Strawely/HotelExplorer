package com.example.hotelexplorer.views

import com.example.hotelexplorer.model.Hotel

interface IHotelsListView {
    fun updateHotelsView(hotels: ArrayList<Hotel>)

    fun showProgress()

    fun hideProgress()
}