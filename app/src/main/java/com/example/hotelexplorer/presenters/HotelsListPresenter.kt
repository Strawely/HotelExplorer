package com.example.hotelexplorer.presenters

import android.content.Context
import android.widget.ArrayAdapter
import com.example.hotelexplorer.*
import com.example.hotelexplorer.views.IHotelsListView
import kotlinx.coroutines.*

class HotelsListPresenter(private var hotelsView: IHotelsListView?) {

    val hotelsListAdapter: HotelsListAdapter = HotelsListAdapter()
    var sortingSpinnerAdapter: ArrayAdapter<CharSequence>? = null
        private set

    suspend fun updateHotelsList() {
        withContext(Dispatchers.Main) {
            hotelsView?.showProgress()
        }
        Repository.hotelsList.clear()
        Repository.hotelsList.addAll(Connector.getHotels())
        withContext(Dispatchers.Main) {
            hotelsView?.updateHotelsView(Repository.hotelsList)
            hotelsView?.hideProgress()
        }
    }

    fun initSpinnerAdapter(context: Context) {
        if (sortingSpinnerAdapter != null) return
        sortingSpinnerAdapter =
            ArrayAdapter.createFromResource(context,
                R.array.sorting_array,
                R.layout.spinner_item
            )
        sortingSpinnerAdapter?.setDropDownViewResource(R.layout.spinner_dropdown_item)
    }

    fun onSpinnerItemSelected(item: Int) {
        when (item) {
            0 -> hotelsListAdapter.hotelsList.sortBy { it.id } // сортировка по умолчанию
            1 -> hotelsListAdapter.hotelsList.sortBy { it.distance } // сортировка по расстоянию
            2 -> hotelsListAdapter.hotelsList.sortBy { it.getSuitesAvailable().size } //сортировка по номерам
        }
        hotelsListAdapter.notifyDataSetChanged()
    }

    fun onDestroy() {
        hotelsView = null
    }
}