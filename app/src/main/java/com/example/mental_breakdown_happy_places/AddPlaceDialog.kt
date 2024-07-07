package com.example.mental_breakdown_happy_places

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.mental_breakdown_happy_places.databinding.ActivityAddPlaceBinding
import com.example.mental_breakdown_happy_places.db.PlaceApplication
import com.example.mental_breakdown_happy_places.db.PlaceViewModel
import com.example.mental_breakdown_happy_places.db.PlaceViewModelFactory
import kotlinx.coroutines.launch


class AddPlaceDialog : AppCompatActivity() {
    private var binding: ActivityAddPlaceBinding? = null



    val placeViewModel : PlaceViewModel by viewModels{
        PlaceViewModelFactory((application as PlaceApplication).repository)
    }


    // Place attributes
    var name = ""
    var description = ""
    var latitude : Double = 0.0
    var longitude :Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAddPlaceBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Initial visibility of buttons
        binding?.buttonAdd?.visibility = View.GONE


        // TextWatchers for EditTexts
        binding?.editPlaceName?.addTextChangedListener(textWatcher)
        binding?.editPlaceDescription?.addTextChangedListener(textWatcher)
        binding?.editPlaceLatitude?.addTextChangedListener(textWatcher)
        binding?.editPlaceLongitude?.addTextChangedListener(textWatcher)


        binding?.buttonCancel?.setOnClickListener {
            finish()
        }


        // Inputs
        binding?.buttonAdd?.setOnClickListener {
            // Name of the place
            name = binding?.editPlaceName?.text.toString()
            // Description of the place
            description = binding?.editPlaceDescription?.text.toString()


            // latitude of the place
            latitude = binding?.editPlaceLatitude?.text.toString().toDoubleOrNull() ?: 0.0
            // longitude of place
            longitude = binding?.editPlaceLongitude?.text.toString().toDoubleOrNull() ?: 0.0


            // Adds new place to DB. Id should be assigned automatically.
            lifecycleScope.launch {
                placeViewModel.insertPlace(0,name,description,latitude,longitude)
            }
            finish()
        }
    }

    // TextWatcher is for checking if an textfield got edited
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val name = binding?.editPlaceName?.text.toString()
            val description = binding?.editPlaceDescription?.text.toString()
            val latitude = binding?.editPlaceLatitude?.text.toString()
            val longitude = binding?.editPlaceLongitude?.text.toString()

            if (name.isNotEmpty() || description.isNotEmpty() || latitude.isNotEmpty() || longitude.isNotEmpty()) {
                binding?.buttonAdd?.visibility = View.VISIBLE

            } else {
                binding?.buttonAdd?.visibility = View.GONE

            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}