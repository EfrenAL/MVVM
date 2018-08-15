package com.example.pickrestaurant.people.overview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.amazonaws.mobile.client.AWSMobileClient
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.overview.event.EventsFragment
import com.example.pickrestaurant.people.overview.people.PeopleFragment
import com.example.pickrestaurant.people.overview.profile.ProfileFragment
import com.example.pickrestaurant.people.utils.OVERVIEW
import com.example.pickrestaurant.people.utils.PARENT
import com.example.pickrestaurant.people.utils.SIGNUP
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_overview.*
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

    private var profileFragment: ProfileFragment = ProfileFragment()
    private var eventsFragment: EventsFragment = EventsFragment()
    private var peopleFragment: PeopleFragment = PeopleFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        AndroidInjection.inject(this) //configureDagger

        showMainFragments(savedInstanceState)
        setNavigationBottom()

        AWSMobileClient.getInstance().initialize(this).execute()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun passData(eventId: String) {
        peopleFragment = PeopleFragment()
        val args = Bundle()
        args.putString(PeopleFragment.EVENT_ID, eventId)
        peopleFragment.arguments = args
        supportFragmentManager.beginTransaction()
                .replace(R.id.fr_people, peopleFragment, null)
                .commit()
    }

    private fun setNavigationBottom() {
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_profile -> showProfileFragment()
                R.id.action_main -> showMainFragments(null)
                else -> false
            }
        }
    }

    private fun showProfileFragment(): Boolean {

        val args = Bundle()
        args.putInt(PARENT, OVERVIEW)
        profileFragment.arguments = args

        showFragment(profileFragment, R.id.activity_overview)
        hideFragment(peopleFragment)
        hideFragment(eventsFragment)
        return true
    }

    private fun showMainFragments(savedInstanceState: Bundle?): Boolean {
        if (savedInstanceState == null) {
            hideFragment(profileFragment)
            showFragment(eventsFragment, R.id.fr_events)
            showFragment(peopleFragment, R.id.fr_people)

        }
        return true
    }

    private fun showFragment(fragment: Fragment, container: Int) {
        if (fragment.isAdded)
            supportFragmentManager.beginTransaction().show(fragment).commit()
        else {
            supportFragmentManager.beginTransaction()
                    .add(container, fragment, null)
                    .commit()
        }
    }

    private fun hideFragment(fragment: Fragment) {
        if (fragment.isAdded)
            supportFragmentManager.beginTransaction().remove(fragment).commit()
    }


}