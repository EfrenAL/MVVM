package com.example.pickrestaurant.people.repositories

import com.example.pickrestaurant.people.base.MyApi
import com.example.pickrestaurant.people.base.UserLoginPostParameter
import com.example.pickrestaurant.people.model.User
import io.reactivex.Observable
import javax.inject.Singleton

/**
 * Created by efren.lamolda on 13.07.18.
 */

@Singleton
class UserRepository(private val myApi: MyApi) {


    private lateinit var userData: User

    fun loginUser(email:String, password: String): Observable<User> {

        return myApi.loginUser(UserLoginPostParameter(email,password))
    }

}