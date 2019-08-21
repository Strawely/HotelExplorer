package com.example.hotelexplorer.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelexplorer.model.Hotel
import com.example.hotelexplorer.views.IHotelsListView
import com.example.hotelexplorer.R
import com.example.hotelexplorer.Repository
import com.example.hotelexplorer.presenters.HotelsListPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

/*
 * Активити для отображения списка отелей
 */
class HotelsListActivity : Activity(), IHotelsListView {

    private var hotelsPresenter: HotelsListPresenter? =
        HotelsListPresenter(this)

    override fun updateHotelsView(hotels: ArrayList<Hotel>) {
        hotelsPresenter?.hotelsListAdapter?.hotelsList?.clear()
        hotelsPresenter?.hotelsListAdapter?.hotelsList?.addAll(Repository.hotelsList)
        hotelsPresenter?.hotelsListAdapter?.notifyDataSetChanged()
        spinner_list_sorting.setSelection(0)
    }

    private fun initHotelsRecycler() {
        recycler_list.adapter = hotelsPresenter?.hotelsListAdapter
        recycler_list.layoutManager = LinearLayoutManager(this)
    }

    private fun initSortingSpinner() {
        hotelsPresenter?.initSpinnerAdapter(applicationContext)
        spinner_list_sorting.adapter = hotelsPresenter?.sortingSpinnerAdapter
        spinner_list_sorting.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, selectedItem: Int, selectedId: Long) {
                hotelsPresenter?.onSpinnerItemSelected(selectedItem)
            }

        }
    }

    override fun showProgress() {
        progress_list.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_list.visibility = View.GONE
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initHotelsRecycler()
        initSortingSpinner()

        GlobalScope.launch {
            hotelsPresenter?.updateHotelsList()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        hotelsPresenter?.onDestroy()
        hotelsPresenter = null
    }

    fun onRefreshClick(v: View) {
        GlobalScope.launch { hotelsPresenter?.updateHotelsList() }
    }
}
