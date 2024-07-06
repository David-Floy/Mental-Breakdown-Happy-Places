package com.example.mental_breakdown_happy_places

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mental_breakdown_happy_places.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private  var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.testadd?.setOnClickListener{

            // intent erzeugen, um zur AddPlaceDialog Activity zu wechseln
            val intent = Intent(this, AddPlaceDialog::class.java)
            startActivity(intent)
        }
        binding?.showDB?.setOnClickListener{

            // intent erzeugen, um zur AddPlaceDialog Activity zu wechseln
            val intent = Intent(this, PlaceList::class.java)
            startActivity(intent)
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}