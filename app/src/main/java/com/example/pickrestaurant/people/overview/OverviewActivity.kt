package com.example.pickrestaurant.people.overview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
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
class OverviewActivity : AppCompatActivity(), HasSupportFragmentInjector {

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