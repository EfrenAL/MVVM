package com.example.pickrestaurant.people.injection.module

import com.example.pickrestaurant.people.overview.event.EventsFragment
import com.example.pickrestaurant.people.overview.people.PeopleFragment
import com.example.pickrestaurant.people.overview.profile.ProfileFragment
import com.example.pickrestaurant.people.signup.SignUpFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by efren.lamolda on 25.07.18.
 */
@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributePeopleFragment(): PeopleFragment

    @ContributesAndroidInjector
    abstract fun contributeEventsFragment(): EventsFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun contributeSignUpFragment(): SignUpFragment
}