package com.example.mental_breakdown_happy_places

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mental_breakdown_happy_places.db.Place

class PlaceListRecyclerViewAdapter(private val context: Context) :
    RecyclerView.Adapter<PlaceListRecyclerViewAdapter.MyViewHolder>() {

    // List of places
    private var placeList: List<Place> = emptyList()



    // Create the ViewHolder
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceListRecyclerViewAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.place_list_row, parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(
        holder: PlaceListRecyclerViewAdapter.MyViewHolder,
        position: Int
    ) {
        // Get the current name of the place from the list of places
        val currentPlaceName : String = placeList[position].name

        holder.nameTextView.text = currentPlaceName


        // Set OnClickListener for the item from the RecyclerView list
        holder.itemView.setOnClickListener {val intent = Intent(context, PlaceDetailActivity::class.java)
            intent.putExtra("PLACE_ID", placeList[position].id)  // Pass the ID or any other necessary data
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return placeList.size
    }


    // Function to update the list of places
    fun setPlaces(placeList: List<Place>) {
        this.placeList = placeList
        notifyDataSetChanged()
    }




    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val nameTextView : TextView = itemView.findViewById(R.id.place_Name)
    }

}