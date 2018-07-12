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
    fun createUser(): Observable<User>

    /*@FormUrlEncoded
    @POST("/users/login")
    fun loginUser2(@Field("email") email: String,
                  @Field("password") password: String): Observable<User>    @FormUrlEncoded*/

    @POST("/users/login")
    fun loginUser(@Body post: UserLoginPostParameter): Observable<User>

}

data class UserLoginPostParameter(var email: String, var password: String)
