package com.example.pickrestaurant.people.overview

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.amazonaws.mobile.client.AWSMobileClient
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.overview.event.EventsFragment
import com.example.pickrestaurant.people.overview.events.EventsDetailsFragment
import com.example.pickrestaurant.people.overview.people.PeopleFragment
import com.example.pickrestaurant.people.overview.profile.ProfileFragment
import com.example.pickrestaurant.people.personDetails.PersonDetailsActivity
import com.example.pickrestaurant.people.utils.OVERVIEW
import com.example.pickrestaurant.people.utils.PARENT
import com.example.pickrestaurant.people.utils.USERID
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_overview.*
import javax.inject.Inject


/**
 * Created by efren.lamolda on 13.07.18.
 */
class OverviewActivity : AppCompatActivity(), HasSupportFragmentInjector, EventsFragment.DataPassListener, PeopleFragment.StartActivityWithAnimation {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    private var profileFragment: ProfileFragment = ProfileFragment()
    private var eventsFragment: EventsFragment = EventsFragment()
    private var peopleFragment: PeopleFragment = PeopleFragment()
    private var eventsDetailsFragment: EventsDetailsFragment = EventsDetailsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        AndroidInjection.inject(this) //configureDagger

        showMainFragments()
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
        bottom_navigation.selectedItemId = R.id.action_main
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_profile -> showProfileFragment()
                R.id.action_main -> showMainFragments()
                R.id.action_events -> {
                    showEventsDetailsFragment()
                    true
                }
                else -> false
            }
        }
    }

    private fun showEventsDetailsFragment() {
        supportFragmentManager.popBackStackImmediate()
        if(peopleFragment.isVisible)
            supportFragmentManager.beginTransaction()
                    .hide(peopleFragment)
                    .commit()
        supportFragmentManager.beginTransaction()
                .replace(R.id.fr_events, eventsDetailsFragment, null)
                .commit()
    }

    private fun showProfileFragment(): Boolean {

        val args = Bundle()
        args.putInt(PARENT, OVERVIEW)
        profileFragment.arguments = args

        supportFragmentManager.popBackStackImmediate()

        supportFragmentManager.beginTransaction()
                .replace(R.id.fr_events, profileFragment, null)
                .commit()

        return true
    }


    private fun showMainFragments(): Boolean {

        supportFragmentManager.popBackStackImmediate()
        supportFragmentManager.beginTransaction()
                .replace(R.id.fr_events, eventsFragment, null)
                .replace(R.id.fr_people, peopleFragment, null)
                .commit()

        return true
    }

    override fun startActivity(userId: Int, sharedElement: ImageView) {
        val intent = Intent(this, PersonDetailsActivity::class.java)
        intent.putExtra(USERID, userId)

        ActivityOptionsCompat.makeSceneTransitionAnimation(this)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                sharedElement, ViewCompat.getTransitionName(sharedElement)!!)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options.toBundle())
        } else
            startActivity(intent)
    }


}