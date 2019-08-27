package com.example.hotelexplorer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.hotelexplorer.model.Hotel
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

object Connector {

    private const val BASE_URL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/"

    private val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val api = retrofit.create(IApi::class.java)

    /**
     * Метод загрузки списка отелей
     */
    suspend fun getHotels(): MutableList<Hotel> {
        var hotels: MutableList<Hotel> = ArrayList()
        try {
            val response = api.getHotels().execute()
            if (response.body() == null) return hotels
            hotels = response.body() ?: hotels
            delay(2000)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return hotels
    }

    /**
     * Метод загрузки подробной информации об отеле
     */
    suspend fun getSingleHotel(hotelId: Long): Hotel? {
        return try {
            val response = api.getSingleHotel(hotelId).execute()
            response.body()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Метод загрузки изображения по названию файла
     */
    suspend fun getImage(imageName: String): Bitmap {
        return try {
            val url = URL(BASE_URL + imageName)
            BitmapFactory.decodeStream(url.openConnection().getInputStream())
        }catch (e: Exception){
            e.printStackTrace()
            Bitmap.createBitmap(100, 100, Bitmap.Config.ALPHA_8)
        }
    }
}