package com.example.hotelexplorer

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelexplorer.activities.HotelActivity
import com.example.hotelexplorer.model.Hotel

class HotelsListAdapter(val hotelsList: ArrayList<Hotel> = ArrayList()) :
    RecyclerView.Adapter<HotelsListAdapter.HotelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.hotel_item, parent, false)
        return HotelViewHolder(view)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val hotel = hotelsList[position]
        holder.textName.text = hotel.name
        holder.textAddress.text = hotel.address
        holder.textStars.text = hotel.stars.toString()
        holder.textDistance.text = hotel.distance.toString()
        holder.textSuites.text = hotel.getFormattedSuitsAvailability()
        holder.itemView.setOnClickListener{
            val intent = Intent(it.context, HotelActivity::class.java)
                .putExtra(it.context.getString(R.string.hotel_id), hotel.id)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount() = hotelsList.size

    class HotelViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textName: TextView = view.findViewById(R.id.text_hotel_item_name)
        val textAddress: TextView = view.findViewById(R.id.text_hotel_item_address)
        val textStars: TextView = view.findViewById(R.id.text_hotel_item_stars)
        val textDistance: TextView = view.findViewById(R.id.text_hotel_item_distance)
        val textSuites: TextView = view.findViewById(R.id.text_hotel_item_suites)
    }
}
