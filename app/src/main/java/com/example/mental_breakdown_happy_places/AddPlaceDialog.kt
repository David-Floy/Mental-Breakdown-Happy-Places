package com.example.mental_breakdown_happy_places

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mental_breakdown_happy_places.databinding.DialogAddPlaceBinding
import kotlinx.coroutines.launch


class AddPlaceDialog : AppCompatActivity() {
    private var binding: DialogAddPlaceBinding? = null



    private val placeViewModel : PlaceViewModel by viewModels{
        PlaceViewModelFactory((application as PlaceApplication).repository)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAddPlaceBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Initial visibility of buttons
        binding?.buttonAdd?.visibility = View.GONE
        binding?.buttonCancel?.visibility = View.GONE

        // TextWatchers for EditTexts
        binding?.editPlaceName?.addTextChangedListener(textWatcher)
        binding?.editPlaceDescription?.addTextChangedListener(textWatcher)
        binding?.editPlaceLatitude?.addTextChangedListener(textWatcher)
        binding?.editPlaceLongitude?.addTextChangedListener(textWatcher)

        binding?.buttonCancel?.setOnClickListener {
            finish()
        }

        binding?.buttonAdd?.setOnClickListener {
            val name = binding?.editPlaceName?.text.toString()
            val description = binding?.editPlaceDescription?.text.toString()
            val latitude = binding?.editPlaceLatitude?.text.toString().toDoubleOrNull() ?: 0.0
            val longitude = binding?.editPlaceLongitude?.text.toString().toDoubleOrNull() ?: 0.0
            //val place = Place( 0,name, description, latitude, longitude)
            lifecycleScope.launch {
                placeViewModel.insertPlace(0,name,description,latitude,longitude)
            }
            finish()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val name = binding?.editPlaceName?.text.toString()
            val description = binding?.editPlaceDescription?.text.toString()
            val latitude = binding?.editPlaceLatitude?.text.toString()
            val longitude = binding?.editPlaceLongitude?.text.toString()

            if (name.isNotEmpty() || description.isNotEmpty() || latitude.isNotEmpty() || longitude.isNotEmpty()) {
                binding?.buttonAdd?.visibility = View.VISIBLE
                binding?.buttonCancel?.visibility = View.VISIBLE
            } else {
                binding?.buttonAdd?.visibility = View.GONE
                binding?.buttonCancel?.visibility = View.GONE
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }
}