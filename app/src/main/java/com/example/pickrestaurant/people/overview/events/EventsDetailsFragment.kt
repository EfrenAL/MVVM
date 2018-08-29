package com.example.pickrestaurant.people.overview.events

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.model.Event
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_events_details.*
import javax.inject.Inject

/**
 * Created by efren.lamolda on 15.08.18.
 */
class EventsDetailsFragment : Fragment() {


    @Inject
    lateinit var eventsDetailsViewModelFactory: EventsDetailsViewModelFactory
    private lateinit var viewModel: EventsDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_events_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //configureDagger
        AndroidSupportInjection.inject(this)

        activity!!.toolbar_events_details.title = getString(R.string.app_name)

        //configureViewModel
        viewModel = ViewModelProviders.of(this, eventsDetailsViewModelFactory).get(EventsDetailsViewModel::class.java)
        viewModel.events.observe(this, Observer { showEvents(it) })
        viewModel.getEvents()
        //Setup recycleview
        rv_events_details_list.layoutManager = LinearLayoutManager(activity!!.baseContext, LinearLayoutManager.VERTICAL, false)
    }

    private fun showEvents(list: List<Event>?) {
        rv_events_details_list.adapter = EventsDetailsAdapter(list!!, context!!)
    }


}