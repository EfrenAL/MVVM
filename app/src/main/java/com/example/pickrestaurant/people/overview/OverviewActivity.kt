package com.example.pickrestaurant.people.overview

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.EditText
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.model.Event
import com.example.pickrestaurant.people.overview.event.EventViewModel
import com.example.pickrestaurant.people.overview.event.EventViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_overview.*
import javax.inject.Inject


/**
 * Created by efren.lamolda on 13.07.18.
 */
class OverviewActivity : AppCompatActivity() {

    @Inject
    lateinit var eventViewModelFactory: EventViewModelFactory
    private lateinit var viewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)
        AndroidInjection.inject(this)
        showEvents(listOf())

        viewModel = ViewModelProviders.of(this, eventViewModelFactory).get(EventViewModel::class.java)
        viewModel.events.observe(this, Observer { showEvents(it) })

        rv_event_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun showEvents(list: List<Event>?) {

        var arrayList = ArrayList(list)
        arrayList.add(0, Event(0, "New event", "AddEvent", ""))

        rv_event_list.adapter = EventAdapter(arrayList!!, this, clickHandler())
    }

    private fun clickHandler(): EventAdapter.OnItemClickListener {
        return object : EventAdapter.OnItemClickListener {
            override fun onItemClick(item: Event, position: Int) {
                if (position == 0){
                    showDialog()
                }
            }
        }
    }


    private fun showDialog() {

        val view = layoutInflater.inflate(R.layout.event_code_dialog, null)
        val eventCode = view.findViewById<EditText>(R.id.et_event_code)

        AlertDialog.Builder(this)
                .setTitle(getString(R.string.add_event))
                .setView(view)
                .setPositiveButton(android.R.string.ok) { dialog, _ ->

            var isValid = true
            if (eventCode.text.isBlank()) {
                eventCode.error = getString(R.string.post_error)
                isValid = false
            }

            if (isValid) {
                viewModel.postEvent(eventCode.text.toString())
            }

            if (isValid) {
                dialog.dismiss()
            }
        }.setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.cancel()
        }.show()
    }

}