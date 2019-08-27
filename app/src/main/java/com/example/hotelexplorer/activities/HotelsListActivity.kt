package com.example.hotelexplorer.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelexplorer.R
import com.example.hotelexplorer.Repository
import com.example.hotelexplorer.model.Hotel
import com.example.hotelexplorer.presenters.HotelsListPresenter
import com.example.hotelexplorer.views.IHotelsListView
import kotlinx.android.synthetic.main.activity_hotels_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Активити для отображения списка отелей
 */
class HotelsListActivity : AppCompatActivity(), IHotelsListView {

    private var hotelsPresenter: HotelsListPresenter =
        HotelsListPresenter(this)

    override fun updateHotelsView(hotels: ArrayList<Hotel>) {
        hotelsPresenter.hotelsListAdapter.hotelsList.clear()
        hotelsPresenter.hotelsListAdapter.hotelsList.addAll(Repository.hotelsList)
        hotelsPresenter.hotelsListAdapter.notifyDataSetChanged()
        spinner_list_sorting.setSelection(0)
    }

    /**
     * Инициализация recycler view
     */
    private fun initHotelsRecycler() {
        recycler_list.adapter = hotelsPresenter.hotelsListAdapter
        recycler_list.layoutManager = LinearLayoutManager(this)
    }

    /**
     * Инициализация спиннера с вариантами сортировки
     */
    private fun initSortingSpinner() {
        val sortingSpinnerAdapter =
            ArrayAdapter.createFromResource(this, R.array.sorting_array, R.layout.spinner_item)
        sortingSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinner_list_sorting.adapter = sortingSpinnerAdapter
        spinner_list_sorting.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                selectedItem: Int,
                selectedId: Long
            ) {
                hotelsPresenter.onSpinnerItemSelected(selectedItem)
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
        setContentView(R.layout.activity_hotels_list)
        setSupportActionBar(toolbar_list)
        initHotelsRecycler()
        initSortingSpinner()
        lifecycle.addObserver(hotelsPresenter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_refresh){
            hotelsPresenter.updateHotelsList()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
