package com.example.hotelexplorer.activities

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelexplorer.R
import com.example.hotelexplorer.model.Hotel
import com.example.hotelexplorer.presenters.HotelPresenter
import com.example.hotelexplorer.views.IHotelView
import kotlinx.android.synthetic.main.activity_hotel.*

class HotelActivity : AppCompatActivity(), IHotelView {

    private var presenter: HotelPresenter? = null

    override fun fillImageView(bitmap: Bitmap) {
        image_hotel.setImageBitmap(bitmap)
    }

    override fun fillTextViews(hotel: Hotel) {
        text_hotel_name.text = hotel.name
        text_hotel_address.text = hotel.address
        rating_hotel_stars.rating = hotel.stars
        text_hotel_distance.text = hotel.distance.toString()
        text_hotel_suites.text = hotel.getFormattedSuitsAvailability()
        text_hotel_coordinates.text = hotel.getFormattedCoordinates()
    }

    /**
     * Отображение сообщения в случае некорректной передачи id отеля в активити
     */
    override fun showMsgWrongId() {
        Toast.makeText(applicationContext, "Ошибка загрузки отеля", Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel)
        val hotelId = intent.getLongExtra(getString(R.string.hotel_id), -1L)
        presenter = HotelPresenter(this, hotelId)
        lifecycle.addObserver(presenter?:return)
    }

    fun onBackBtnClick(view: View) {
        onBackPressed()
    }
}
