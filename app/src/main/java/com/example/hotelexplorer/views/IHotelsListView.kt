package com.example.hotelexplorer.views

import com.example.hotelexplorer.model.Hotel

interface IHotelsListView {

    /**
     * Метод обновления списка отелей
     */
    fun updateHotelsView(hotels: ArrayList<Hotel>)

    fun showProgress()

    fun hideProgress()
}