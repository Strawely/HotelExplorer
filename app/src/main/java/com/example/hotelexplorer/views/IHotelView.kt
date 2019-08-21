package com.example.hotelexplorer.views

import android.graphics.Bitmap
import com.example.hotelexplorer.model.Hotel

interface IHotelView {
    /**
     * Метод загрузки изображения во вью
     */
    fun initImage(bitmap: Bitmap)

    /**
     * Отображение ошибки загрузки отеля
     */
    fun showMsgWrongId()

    /**
     * Метод заполнения текстовых полей
     */
    fun fillTextViews(hotel: Hotel)
}