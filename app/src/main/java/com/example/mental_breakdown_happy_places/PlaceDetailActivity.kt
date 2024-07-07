package com.example.mental_breakdown_happy_places

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mental_breakdown_happy_places.databinding.ActivityPlaceDetailBinding
import com.example.mental_breakdown_happy_places.db.Place
import com.example.mental_breakdown_happy_places.db.PlaceApplication
import com.example.mental_breakdown_happy_places.db.PlaceViewModel
import com.example.mental_breakdown_happy_places.db.PlaceViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.content.Intent

class PlaceDetailActivity : AppCompatActivity() {

    private val placeViewModel : PlaceViewModel by viewModels {
        PlaceViewModelFactory((application as PlaceApplication).repository)
    }

    private  var binding: ActivityPlaceDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPlaceDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        // Place object
        var place : Place

        // Get the place ID from the intent
        val placeId = intent.getIntExtra("PLACE_ID", -1)

        if (placeId != -1) {
            // Get the place from the view model aka. DB
            lifecycleScope.launch(Dispatchers.IO) {
                place =   placeViewModel.getTextById(placeId)

                // Set the place name and description
                withContext(Dispatchers.Main) {

                    // To access th other attributes of the place, use the place object
                    // for example, binding?.ID_of_the_attribute from layout?.text = place.name
                    binding?.detailPlaceName?.text = place.name
                    binding?.detailPlaceDescription?.text = place.description

                }
            }




        }

        // Button Click Listener
        binding?.button?.setOnClickListener {
            val intent = Intent(this, PlaceList::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
