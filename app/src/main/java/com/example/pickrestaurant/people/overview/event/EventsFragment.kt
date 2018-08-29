package com.example.pickrestaurant.people.overview.event

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.model.Event
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_overview.*
import kotlinx.android.synthetic.main.fragment_events.*
import javax.inject.Inject



/**
 * Created by efren.lamolda on 25.07.18.
 */
class EventsFragment : Fragment() {

    interface DataPassListener {
        fun passData(eventId: String)
    }

    @Inject
    lateinit var eventViewModelFactory: EventViewModelFactory
    private lateinit var viewModel: EventViewModel

    private var mCallback: DataPassListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //configureDagger
        AndroidSupportInjection.inject(this)

        activity!!.toolbar_main.title = getString(R.string.app_name)

        showEvents(listOf())
        //configureViewModel
        viewModel = ViewModelProviders.of(this, eventViewModelFactory).get(EventViewModel::class.java)
        viewModel.events.observe(this, Observer { showEvents(it) })
        //Setup recycleview
        rv_event_list.layoutManager = LinearLayoutManager(activity!!.baseContext, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mCallback = activity as DataPassListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener")
        }
    }

    private fun showEvents(list: List<Event>?) {
        var arrayList = ArrayList(list)
        arrayList.add(0, Event(0, "New event", "AddEvent", "","",""))

        rv_event_list.adapter = EventAdapter(arrayList!!, activity!!.baseContext, clickHandler())
    }

    private fun clickHandler(): EventAdapter.OnItemClickListener {
        return object : EventAdapter.OnItemClickListener {
            override fun onItemClick(event: Event, position: Int) {
                if (position == 0) {
                    showDialog()
                } else {
                    activity!!.toolbar_main.title = event.name
                    mCallback!!.passData(event.id.toString())
                }
            }
        }
    }


    private fun showDialog() {

        val view = layoutInflater.inflate(R.layout.dialog_event_code, null)
        val eventCode = view.findViewById<EditText>(R.id.et_event_code)

        AlertDialog.Builder(activity!!)
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
                }.setNegativeButton(android.R.string.cancel) { dialog, _ ->
                    dialog.cancel()
                }.show()
    }
}