package com.example.pickrestaurant.people

import android.app.Activity
import android.app.Application
import com.example.pickrestaurant.people.injection.component.DaggerAppComponent
import com.example.pickrestaurant.people.injection.module.AppModule
import com.example.pickrestaurant.people.injection.module.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by efren.lamolda on 24.07.18.
 */
class MyApplication: Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
                .builder()
                .appModule(AppModule(this, baseContext))
                .networkModule(NetworkModule)
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}