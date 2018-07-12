package com.example.pickrestaurant.people.base

import com.example.pickrestaurant.people.model.User
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by efren on 12/07/2018.
 */
interface MyApi {

    @POST("/users")
    fun createUser(@Body post: UserSignupPostParameter): Observable<User>

    @POST("/users/login")
    fun loginUser(@Body post: UserLoginPostParameter): Observable<User>

}

data class UserLoginPostParameter(var email: String, var password: String)

data class UserSignupPostParameter(var name: String, var email: String, var password: String)
