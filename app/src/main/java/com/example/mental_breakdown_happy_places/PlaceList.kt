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
import com.example.mental_breakdown_happy_places.databinding.ActivityPlaceListBinding
import android.widget.Button
import android.content.Intent



class PlaceList : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var placeAdapter: PlaceListRecyclerViewAdapter
    // private lateinit var binding: ActivityPlaceListBinding --- hab jetzt mal doch kein binding benutzt hier ---

    private val placeViewModel : PlaceViewModel by viewModels {
    PlaceViewModelFactory((application as PlaceApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_list)

        // set up recyclerview
        newRecyclerView = findViewById(R.id.recyclerView)

        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        // set up adapter
        placeAdapter = PlaceListRecyclerViewAdapter(this)
        newRecyclerView.adapter = placeAdapter

        // set up viewmodel
        placeViewModel.getAllPlaces().observe(this, Observer { placeList ->
            placeList?.let { placeAdapter.setPlaces(it) }
        })

        // Button Click Listener to navigate to MainActivity
        val backToMainButton: Button = findViewById(R.id.backToMainButton)
        backToMainButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        
    }


}
