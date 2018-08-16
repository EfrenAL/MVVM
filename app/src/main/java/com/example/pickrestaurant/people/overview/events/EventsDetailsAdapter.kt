package com.example.pickrestaurant.people.overview.events

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.model.Event
import kotlinx.android.synthetic.main.item_event_card.view.*

class EventsDetailsAdapter(val events: List<Event>, val context: Context) : RecyclerView.Adapter<EventsDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): EventsDetailsViewHolder {
        return EventsDetailsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_event_card, parent, false))
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventsDetailsViewHolder, position: Int) {
        holder.bind(events[position], position)
    }

}

class EventsDetailsViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    private val title_text = view.title_text
    private val subtitle_text = view.subtitle_text
    private val iv_logo = view.media_image

    fun bind(event: Event, position: Int) {

        title_text?.text = event.name
        subtitle_text?.text = event.description
        //iv_logo?.setImageResource(R.drawable.ic_add)
    }
}