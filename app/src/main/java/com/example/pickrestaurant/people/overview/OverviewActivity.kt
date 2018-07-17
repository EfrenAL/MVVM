package com.example.pickrestaurant.people.overview

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.model.Event
import com.example.pickrestaurant.people.overview.event.EventViewModel
import kotlinx.android.synthetic.main.activity_overview.*

/**
 * Created by efren.lamolda on 13.07.18.
 */
class OverviewActivity: AppCompatActivity() {

    private lateinit var viewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        viewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        viewModel.events.observe(this, Observer { showEvents(it) })

        rv_event_list.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
    }

    private fun showEvents(list: ArrayList<Event>?) {
        rv_event_list.adapter = EventAdapter(list!!, this)
    }
}