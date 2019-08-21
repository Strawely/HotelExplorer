package com.example.hotelexplorer.activities

import android.app.Activity
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.hotelexplorer.Connector
import com.example.hotelexplorer.R
import com.example.hotelexplorer.model.Hotel
import com.example.hotelexplorer.presenters.HotelPresenter
import com.example.hotelexplorer.views.IHotelView
import kotlinx.android.synthetic.main.activity_hotel.*

class HotelActivity : Activity(), IHotelView {

    private var presenter: HotelPresenter? = null

    override fun initImage(bitmap: Bitmap) {
        image_hotel.setImageBitmap(bitmap)
    }

    override fun fillTextViews(hotel: Hotel) {
        text_hotel_name.text = hotel.name
        text_hotel_address.text = hotel.address
        text_hotel_stars.text = hotel.stars.toString()
        text_hotel_distance.text = hotel.distance.toString()
        text_hotel_suites.text = hotel.getFormattedSuitsAvailability()
        text_hotel_coordinates.text = hotel.getFormattedCoordinates()
    }

    override fun showMsgWrongId() {
        Toast.makeText(applicationContext, "Ошибка загрузки отеля", Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel)
        val hotelId = intent.getLongExtra(getString(R.string.hotel_id), -1L)
        presenter = HotelPresenter(this, hotelId)
    }
}
