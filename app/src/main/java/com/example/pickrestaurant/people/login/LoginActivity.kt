package com.example.pickrestaurant.people.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.signup.SignupActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by efren on 12/07/2018.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initUi()

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        viewModel.loadingVisibility.observe(this, Observer { progressBar.visibility = it!! })
    }

    private fun initUi() {
        btn_login.setOnClickListener({
            viewModel.loginUser(input_email.text.toString(), input_password.text.toString())
        })

        link_signup.setOnClickListener({
            startActivity(Intent(this, SignupActivity::class.java))
        })
    }

}