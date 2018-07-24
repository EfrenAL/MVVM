package com.example.pickrestaurant.people.overview

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.login.LoginViewModel
import com.example.pickrestaurant.people.login.LoginViewModelFactory
import com.example.pickrestaurant.people.model.Event
import com.example.pickrestaurant.people.overview.event.EventViewModel
import com.example.pickrestaurant.people.overview.event.EventViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_overview.*
import javax.inject.Inject

/**
 * Created by efren.lamolda on 13.07.18.
 */
class OverviewActivity: AppCompatActivity() {

    @Inject
    lateinit var eventViewModelFactory: EventViewModelFactory
    private lateinit var viewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)
        AndroidInjection.inject(this)


        viewModel = ViewModelProviders.of(this, eventViewModelFactory).get(EventViewModel::class.java)
        viewModel.events.observe(this, Observer { showEvents(it) })

        rv_event_list.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
    }

    private fun showEvents(list: List<Event>?) {
        rv_event_list.adapter = EventAdapter(list!!, this)
    }
}