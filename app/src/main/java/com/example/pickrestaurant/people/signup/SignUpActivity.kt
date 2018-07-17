package com.example.pickrestaurant.people.signup

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.login.LoginActivity
import kotlinx.android.synthetic.main.activity_signup.*

/**
 * Created by efren.lamolda on 12.07.18.
 */
class SignUpActivity : AppCompatActivity(){

    private lateinit var viewModel: SignUpViewModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initUi()
        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)

        viewModel.loadingVisibility.observe(this, Observer { progressBar.visibility = it!! })
        viewModel.errorMessage.observe(this, Observer { showError(it) })
        viewModel.signUpSuccess.observe(this, Observer { if (it==true) startActivity(Intent(this, LoginActivity::class.java)) })
    }

    private fun showError(message: Int?) {
        if (message!=null){
            errorSnackbar = Snackbar.make( coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE)
            errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
            errorSnackbar?.show()
        } else {
            errorSnackbar?.dismiss()
        }
    }

    private fun initUi() {
        btn_signup.setOnClickListener({
            viewModel.signUpUser(input_name.text.toString(),input_email.text.toString(),input_password.text.toString())
        })

        link_login.setOnClickListener({
            startActivity(Intent(this, LoginActivity::class.java))
        })
    }
}