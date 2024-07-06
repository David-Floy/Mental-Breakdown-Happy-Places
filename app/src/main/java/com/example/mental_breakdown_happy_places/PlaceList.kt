package com.example.mental_breakdown_happy_places

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mental_breakdown_happy_places.db.PlaceApplication

import com.example.mental_breakdown_happy_places.db.PlaceViewModel
import com.example.mental_breakdown_happy_places.db.PlaceViewModelFactory

class PlaceList : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var placeAdapter: PlaceListRecyclerViewAdapter

    private val placeViewModel : PlaceViewModel by viewModels {
    PlaceViewModelFactory((application as PlaceApplication).repository)
}



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_list)



        newRecyclerView = findViewById(R.id.recyclerView)

        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        placeAdapter = PlaceListRecyclerViewAdapter()
        newRecyclerView.adapter = placeAdapter


        placeViewModel.getAllPlaces().observe(this, Observer { placeList ->
            placeList?.let { placeAdapter.setPlaces(it) }
        })




    }


}