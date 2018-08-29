package com.example.pickrestaurant.people.model

/**
 * Created by efren.lamolda on 13.07.18.
 */
data class Event(var id: Int,
                 var name: String,
                 var description: String,
                 var pictureUrl: String?,
                 var thumbnailUrl: String?,
                 var webUrl: String)