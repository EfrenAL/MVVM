package com.example.pickrestaurant.people.overview.event

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.model.Event
import com.example.pickrestaurant.people.utils.BUCKET_EVENT_URL
import kotlinx.android.synthetic.main.item_event.view.*


/**
 * Created by efren.lamolda on 15.07.18.
 */
class EventAdapter(val events: List<Event>, val context: Context, val listener: OnItemClickListener): RecyclerView.Adapter<EventViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Event, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(context).inflate(R.layout.item_event, parent, false))
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position], listener, position, context)
    }

    override fun getItemCount(): Int {
        return events.size
    }
}

class EventViewHolder (var view: View) : RecyclerView.ViewHolder(view) {

    private val tv_event_name = view.tv_event_name
    private val iv_logo = view.iv_logo

    fun bind(event: Event, listener: EventAdapter.OnItemClickListener, position: Int, context: Context) {

        tv_event_name?.text = event.name
        if (position == 0)
            iv_logo?.setImageResource(R.drawable.ic_add)
        view.setOnClickListener({
            listener.onItemClick( event, position)
        })

        if (!event.thumbnailUrl.isNullOrBlank())
            Glide.with(context)
                    .load(event.thumbnailUrl)
                    .into(iv_logo)
    }
}