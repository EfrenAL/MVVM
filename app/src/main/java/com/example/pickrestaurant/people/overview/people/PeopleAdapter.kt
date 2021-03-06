package com.example.pickrestaurant.people.overview.people

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.model.User
import com.example.pickrestaurant.people.utils.loadImage
import kotlinx.android.synthetic.main.item_people.view.*


/**
 * Created by efren.lamolda on 25.07.18.
 */
class PeopleAdapter(val people: List<User>, private val context: Context, private val listener: OnItemClickListener) : RecyclerView.Adapter<UserViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: User, position: Int, ivPicture: ImageView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(people[position], listener, position, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(context).inflate(R.layout.item_people, parent, false))
    }

    override fun getItemCount(): Int {
        return people.size
    }
}

class UserViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    private val tvName = view.tv_name
    private val tvDescription = view.tv_description
    private val ivPicture = view.iv_picture

    fun bind(user: User, listener: PeopleAdapter.OnItemClickListener, position: Int, context: Context) {

        tvName?.text = user.name
        tvDescription?.text = user.description
        view.setOnClickListener {
            listener.onItemClick(user, position, ivPicture)
        }
        if (!user.pictureUrl.isNullOrBlank())
            user.loadImage(context, ivPicture)
    }
}