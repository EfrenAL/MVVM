package com.example.pickrestaurant.people.overview.people

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.overview.event.EventViewModel
import com.example.pickrestaurant.people.overview.event.EventViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by efren.lamolda on 25.07.18.
 */
class PeopleFragment: Fragment() {

    @Inject
    lateinit var eventViewModelFactory: EventViewModelFactory
    private lateinit var viewModel: EventViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //configureDagger
        AndroidSupportInjection.inject(this)
        //configureViewModel
        viewModel = ViewModelProviders.of(this, eventViewModelFactory).get(EventViewModel::class.java)
        viewModel.events.observe(this, Observer { Toast.makeText(context,"test", Toast.LENGTH_SHORT) })
    }


}