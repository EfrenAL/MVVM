package com.example.pickrestaurant.people.signup

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.login.LoginActivity
import kotlinx.android.synthetic.main.activity_signup.*

/**
 * Created by efren.lamolda on 12.07.18.
 */
class SignupActivity : AppCompatActivity(){

    private lateinit var viewModel: SignupViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initUi()
        viewModel = ViewModelProviders.of(this).get(SignupViewModel::class.java)
        viewModel.loadingVisibility.observe(this, Observer { progressBar.visibility = it!! })
    }

    private fun initUi() {
        btn_signup.setOnClickListener({
            viewModel.signupUser(input_name.text.toString(),input_email.text.toString(),input_password.text.toString())
        })

        link_login.setOnClickListener({
            startActivity(Intent(this, LoginActivity::class.java))
        })
    }
}