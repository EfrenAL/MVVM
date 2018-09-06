package com.example.pickrestaurant.people.personDetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.model.User
import com.example.pickrestaurant.people.utils.USERID
import com.example.pickrestaurant.people.utils.loadImage
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_people_details.*
import javax.inject.Inject


class PersonDetailsActivity : AppCompatActivity() {


    @Inject
    lateinit var viewModelFactory: PersonDetailsViewModelFactory
    private lateinit var viewModel: PersonDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_details)
        AndroidInjection.inject(this)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val userId = intent.getIntExtra(USERID,0)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PersonDetailsViewModel::class.java)
        viewModel.getPersonDetails(userId)
        viewModel.person.observe(this, Observer { initUi(it) })
    }

    private fun initUi(user: User?) {
        person_name.text = user!!.name
        user.loadImage(this,iv_profile_pic)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}