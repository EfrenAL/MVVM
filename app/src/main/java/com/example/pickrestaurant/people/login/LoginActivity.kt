package com.example.pickrestaurant.people.login

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.pickrestaurant.people.R
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by efren on 12/07/2018.
 */
class LoginActivity: AppCompatActivity() {

    //private lateinit var binding: ActivityUserBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        //binding.postList.layoutManager


        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        //binding.viewModel = viewModel
        initUi()

    }

    private fun initUi() {
        btn_login.setOnClickListener({
            viewModel.loginUser("efren@test.com", "1234567")
        })
    }


}