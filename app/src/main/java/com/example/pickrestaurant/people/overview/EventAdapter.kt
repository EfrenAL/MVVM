package com.example.pickrestaurant.people.overview

import android.content.Context
import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.model.Event
import kotlinx.android.synthetic.main.event_item.view.*

/**
 * Created by efren.lamolda on 15.07.18.
 */
class EventAdapter(val events: List<Event>, val context: Context): RecyclerView.Adapter<EvnetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvnetViewHolder {
        return EvnetViewHolder(LayoutInflater.from(context).inflate(R.layout.event_item, parent, false))
    }

    override fun onBindViewHolder(holder: EvnetViewHolder, position: Int) {
        holder?.tv_event_name?.text = events.get(position).name
    }

    override fun getItemCount(): Int {
        return events.size
    }
}

class EvnetViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val tv_event_name = view.tv_event_name
}