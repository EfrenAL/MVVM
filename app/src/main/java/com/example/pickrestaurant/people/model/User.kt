package com.example.pickrestaurant.people.model

/**
 * Created by efren on 12/07/2018.
 */
data class User(val userId: Int, val name: String, val email: String, val password: String, val description: String, var authToken: String)