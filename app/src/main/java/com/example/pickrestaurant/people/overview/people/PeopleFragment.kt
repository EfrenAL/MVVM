package com.example.pickrestaurant.people.overview.people

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.model.User
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_people.*
import javax.inject.Inject

/**
 * Created by efren.lamolda on 25.07.18.
 */
class PeopleFragment: Fragment() {

    @Inject
    lateinit var peopleViewModelFactory: PeopleViewModelFactory
    private lateinit var viewModel: PeopleViewModel

    companion object {
        const val EVENT_ID = "event_id"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //configureDagger
        AndroidSupportInjection.inject(this)
        //configureViewModel
        viewModel = ViewModelProviders.of(this, peopleViewModelFactory).get(PeopleViewModel::class.java)
        viewModel.people.observe(this, Observer { showPeople(it) })

        rv_people.layoutManager = LinearLayoutManager(activity!!.baseContext, LinearLayoutManager.VERTICAL, false)
    }

    override fun onStart() {
        super.onStart()
        val args = arguments
        if (args != null) {
            viewModel.getPeople(args.getString(EVENT_ID))
        }
    }

    private fun showPeople(people: List<User>?) {
        rv_people.adapter = PeopleAdapter(people!!, activity!!.baseContext, clickHandler())
    }

    private fun clickHandler(): PeopleAdapter.OnItemClickListener {
        return object : PeopleAdapter.OnItemClickListener {
            override fun onItemClick(item: User, position: Int) {

            }
        }
    }
}