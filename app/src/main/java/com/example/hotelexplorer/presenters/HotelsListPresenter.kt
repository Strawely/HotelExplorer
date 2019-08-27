package com.example.hotelexplorer.presenters

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.hotelexplorer.*
import com.example.hotelexplorer.model.Hotel
import com.example.hotelexplorer.views.IHotelsListView
import kotlinx.coroutines.*

class HotelsListPresenter(private var hotelsView: IHotelsListView?) : LifecycleObserver {

    val hotelsListAdapter: HotelsListAdapter = HotelsListAdapter()

    /**
     * Обновляет список отелей
     */
    fun updateHotelsList() {
        GlobalScope.launch {
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
    }

    /**
     * Метод сортировки отелей в зависимости от выбора пользователя
     */
    fun onSpinnerItemSelected(item: Int) {
        when (item) {
            0 -> hotelsListAdapter.hotelsList.sortBy { hotel: Hotel -> hotel.id } // сортировка по умолчанию
            1 -> hotelsListAdapter.hotelsList.sortBy { it.distance } // сортировка по расстоянию
            2 -> hotelsListAdapter.hotelsList.sortBy {
                it.getSuitesAvailable().size
            } //сортировка по номерам
        }
        hotelsListAdapter.notifyDataSetChanged()
    }

    /**
     * Удаляет ссылку на вью, чтобы от греха ничего не утекло
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        hotelsView = null
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        if (Repository.hotelsList.isEmpty()) {
            GlobalScope.launch {
                updateHotelsList()
            }
        } else {
            hotelsListAdapter.hotelsList.addAll(Repository.hotelsList)
            hotelsListAdapter.notifyDataSetChanged()
        }
    }
}