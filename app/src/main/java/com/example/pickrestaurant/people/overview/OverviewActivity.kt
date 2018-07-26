package com.example.pickrestaurant.people.overview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.overview.event.EventsFragment
import com.example.pickrestaurant.people.overview.people.PeopleFragment
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


/**
 * Created by efren.lamolda on 13.07.18.
 */
class OverviewActivity : AppCompatActivity(), HasSupportFragmentInjector, EventsFragment.DataPassListener {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        AndroidInjection.inject(this) //configureDagger

        showFragment(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun passData(eventId: String) {
        val peopleFragment = PeopleFragment()
        val args = Bundle()
        args.putString(PeopleFragment.EVENT_ID, eventId)
        peopleFragment.arguments = args
        supportFragmentManager.beginTransaction()
                .replace(R.id.fr_people, peopleFragment,null)
                .commit()
    }

    private fun showFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fr_events, EventsFragment(), null)
                    .commit()

            supportFragmentManager.beginTransaction()
                    .add(R.id.fr_people, PeopleFragment(), null)
                    .commit()
        }
    }


}