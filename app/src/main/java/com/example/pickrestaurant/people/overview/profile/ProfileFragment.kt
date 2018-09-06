package com.example.pickrestaurant.people.overview.profile

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pickrestaurant.people.R
import com.example.pickrestaurant.people.signup.SignUpActivity
import com.example.pickrestaurant.people.utils.PARENT
import com.example.pickrestaurant.people.utils.SIGNUP
import com.example.pickrestaurant.people.utils.loadImage
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile_content2.*
import javax.inject.Inject


/**
 * Created by efren.lamolda on 26.07.18.
 */
class ProfileFragment : Fragment() {

    interface DataPassListener {
        fun goToMain()
    }

    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var viewModel: ProfileViewModel
    private var parent: Int = 0

    private var callBack: DataPassListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        parent = arguments!!.getInt(PARENT, 0)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //configureDagger
        AndroidSupportInjection.inject(this)
        //configureViewModel
        viewModel = ViewModelProviders.of(this, profileViewModelFactory).get(ProfileViewModel::class.java)
        viewModel.user.observe(this, Observer { updateUi() })
        viewModel.loadingVisibility.observe(this, Observer { pb_profile.visibility = it!! })
        viewModel.updateSuccess.observe(this, Observer { if (it == true) notifyUser() })
        updateUi()
        setFab()
    }

    private fun updateUi() {
        var user = viewModel.user.value!!
        et_email.setText(user.email)
        et_name.setText(user.name)
        et_description.setText(user.description)

        if (user.pictureUrl!= null && user.pictureUrl.isNotEmpty())
            user.loadImage(context!!, iv_profile_pic)

        btn_update.setOnClickListener { viewModel.updateUser(et_name.text.toString(), et_description.text.toString(), "") }
    }

    private fun setFab() {
        fab.setOnClickListener {
            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePicture, 0)//zero can be replaced with any action code
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            if (activity is SignUpActivity)
                callBack = activity as DataPassListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement DataPassListener")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> {
                if (resultCode === RESULT_OK) {
                    viewModel.setImage(data!!.extras.get("data") as Bitmap)
                }
            }
        }
    }

    private fun notifyUser() {
        if (parent == SIGNUP)
            callBack!!.goToMain()
    }
}