package com.example.pickrestaurant.people.injection.component

import com.example.pickrestaurant.people.MyApplication
import com.example.pickrestaurant.people.injection.module.AppModule
import com.example.pickrestaurant.people.injection.module.BuildersModule
import com.example.pickrestaurant.people.injection.module.NetworkModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by efren.lamolda on 24.07.18.
 */
@Singleton
@Component(
        modules = arrayOf(AndroidInjectionModule::class, BuildersModule::class, AppModule::class, NetworkModule::class)
)
interface AppComponent {
    fun inject(app: MyApplication)

}