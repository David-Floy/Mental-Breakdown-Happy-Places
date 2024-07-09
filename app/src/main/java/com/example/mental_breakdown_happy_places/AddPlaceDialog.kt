package com.example.mental_breakdown_happy_places

import android.Manifest
import android.R
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.mental_breakdown_happy_places.databinding.ActivityAddPlaceBinding
import com.example.mental_breakdown_happy_places.db.PlaceApplication
import com.example.mental_breakdown_happy_places.db.PlaceViewModel
import com.example.mental_breakdown_happy_places.db.PlaceViewModelFactory
import kotlinx.coroutines.launch
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class AddPlaceDialog : AppCompatActivity() {
    private var binding: ActivityAddPlaceBinding? = null

    // Location permission request code
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    val placeViewModel : PlaceViewModel by viewModels{
        PlaceViewModelFactory((application as PlaceApplication).repository)
    }


    // Place attributes
    var name = ""
    var description = ""
    var latitude : Double = 0.0
    var longitude :Double = 0.0
    var geoPoint : GeoPoint? = null
    var map : MapView? = null

    lateinit var marker : Marker

    // Default location for Marker in case GPS is not available
    private val defaultMarkerLocation = GeoPoint(52.5200, 13.4050)


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAddPlaceBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // MapView settings
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        // Initial visibility of buttons
        binding?.buttonAdd?.visibility = View.GONE


        // MapView settings
        map = binding?.addPlaceMapView
        map?.setUseDataConnection(true)
        map?.setTileSource(TileSourceFactory.MAPNIK)
        map?.setMultiTouchControls(true)
        val mapController : IMapController = map?.controller!!



        // Location setup
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            // Permission already granted, proceed with location setup
            // Get the map again
            val mGpsMyLocationProvider = GpsMyLocationProvider(this)
            val mLocationProvider = MyLocationNewOverlay(mGpsMyLocationProvider, map)
            mLocationProvider.enableMyLocation()
            mLocationProvider.enableFollowLocation()
            map?.overlays?.add(mLocationProvider)

            // Run on first fix
            mLocationProvider.runOnFirstFix {
                runOnUiThread {
                    map?.overlays?.clear()
                    //map?.overlays?.add(mLocationProvider)
                    mapController.animateTo(mLocationProvider.myLocation)
                    val center = GeoPoint(mLocationProvider.myLocation)
                    geoPoint = mLocationProvider.myLocation
                    addMarker(geoPoint)

                    // Set the initial values of the EditTexts
                    binding?.editPlaceLatitude?.setText(geoPoint?.latitude.toString())
                    binding?.editPlaceLongitude?.setText(geoPoint?.longitude.toString())

                    // Set the initial zoom level
                    mapController.setZoom(18) // Or your desired zoom level
                    mLocationProvider.disableMyLocation()
                }

            }

        }



        // TextWatchers for EditTexts
        binding?.editPlaceName?.addTextChangedListener(textWatcher)
        binding?.editPlaceLongitude?.addTextChangedListener(textWatcher)
        binding?.editPlaceLatitude?.addTextChangedListener(textWatcher)


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
                    placeViewModel.insertPlace(
                        0,
                        name,
                        description,
                        latitude,
                        longitude,
                        marker.position!!
                    )
                }
                finish()

        }
    }

    // TextWatcher is for checking if an textfield got edited
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val name = binding?.editPlaceName?.text.toString()
            val latitude = binding?.editPlaceLatitude?.text.toString()
            val longitude = binding?.editPlaceLongitude?.text.toString()

            if (name.isNotEmpty() && ::marker.isInitialized) {
                binding?.buttonAdd?.visibility = View.VISIBLE


            } else if (longitude.isNotEmpty()&& latitude.isNotEmpty()) {
                marker.position.longitude = longitude.toDouble()
                map?.controller?.animateTo(marker.position)

                marker.position.latitude = latitude.toDouble()
                map?.controller?.animateTo(marker.position)
            }
            /*else if (latitude.isNotEmpty()) {
                marker.position.latitude = latitude.toDouble()
                map?.controller?.animateTo(marker.position)
            }*/
            else if (name.isEmpty()) {
                binding?.buttonAdd?.visibility = View.GONE

            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with location setup
                val map = binding?.addPlaceMapView // Get the map again
                val mGpsMyLocationProvider = GpsMyLocationProvider(this);val mLocationProvider = MyLocationNewOverlay(mGpsMyLocationProvider, map)
                mLocationProvider.enableMyLocation()
                mLocationProvider.enableFollowLocation()
                map?.overlays?.add(mLocationProvider)

                mLocationProvider.runOnFirstFix {
                    runOnUiThread {
                        map?.overlays?.clear()
                        map?.overlays?.add(mLocationProvider)
                        map?.controller?.animateTo(mLocationProvider.myLocation)
                        map?.controller?.setZoom(18) // Or your desired zoom level
                    }
                }
            } else {
                val map = binding?.addPlaceMapView // Get the map again
                map?.overlays?.clear()
                addMarker(defaultMarkerLocation)
                // Permission denied, handle accordingly (e.g., show a message)
                // You might want to inform the user that location features won't be available
            }
        }


    }

     fun addMarker(center: GeoPoint?) {
        marker = Marker(map)
        marker.setPosition(center)
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.setIcon(getResources().getDrawable(R.drawable.ic_menu_mylocation))
        map?.overlays?.clear()
        map?.overlays?.add(marker)
        map?.invalidate()
    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}