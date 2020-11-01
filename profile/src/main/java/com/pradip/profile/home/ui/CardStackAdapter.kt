package com.pradip.profile.home.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pradip.data.user.local.entities.ProfileL
import com.pradip.data.user.local.models.Location
import com.pradip.data.user.local.models.Name
import com.pradip.data.user.local.models.Picture
import com.pradip.profile.R

class CardStackAdapter(
    private var profiles: List<ProfileL> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.profile_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val profile = profiles[position]
        val name = Gson().fromJson<Name>(profile.name, object : TypeToken<Name>() {}.type)
        val location =
            Gson().fromJson<Location>(profile.location, object : TypeToken<Location>() {}.type)
        val picture =
            Gson().fromJson<Picture>(profile.picture, object : TypeToken<Picture>() {}.type)

        holder.name.text = "${name.title}. ${name.first}"
        holder.city.text = location.city
        Glide.with(holder.image)
            .load(picture.large)
            .into(holder.image)
        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, name.first, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return profiles.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
        var city: TextView = view.findViewById(R.id.item_city)
        var image: AppCompatImageView = view.findViewById(R.id.item_image)
    }

}