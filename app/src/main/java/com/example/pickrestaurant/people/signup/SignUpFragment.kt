package com.example.pickrestaurant.people.signup

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pickrestaurant.people.R
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_signup.*
import javax.inject.Inject

/**
 * Created by efren.lamolda on 15.08.18.
 */
class SignUpFragment: Fragment() {

    interface DataPassListener {
        fun loginSuccess()
        fun goToLogin()
    }

    @Inject
    lateinit var signUpViewModelFactory: SignUpViewModelFactory
    private lateinit var viewModel: SignUpViewModel

    private var errorSnackbar: Snackbar? = null
    private var mCallback: DataPassListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //configureDagger
        AndroidSupportInjection.inject(this)
        //configureViewModel
        viewModel = ViewModelProviders.of(this, signUpViewModelFactory).get(SignUpViewModel::class.java)

        viewModel.loadingVisibility.observe(this, Observer { progressBar.visibility = it!! })
        viewModel.errorMessage.observe(this, Observer { showError(it) })
        viewModel.signUpSuccess.observe(this, Observer { if (it == true) mCallback!!.loginSuccess() })

        initUi()
    }

    private fun showError(message: Int?) {
        if (message != null) {
            errorSnackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE)
            errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
            errorSnackbar?.show()
        } else {
            errorSnackbar?.dismiss()
        }
    }

    private fun initUi() {
        btn_signup.setOnClickListener({
            viewModel.signUpUser(input_name.text.toString(), input_email.text.toString(), input_password.text.toString())
        })

        link_login.setOnClickListener({
            mCallback!!.goToLogin()
        })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mCallback = activity as DataPassListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener")
        }
    }


}