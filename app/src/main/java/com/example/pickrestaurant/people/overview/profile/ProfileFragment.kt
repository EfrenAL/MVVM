package com.example.pickrestaurant.people.overview.profile

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pickrestaurant.people.R
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile_content.*
import javax.inject.Inject

/**
 * Created by efren.lamolda on 26.07.18.
 */
class ProfileFragment: Fragment() {

    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var viewModel: ProfileViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //configureDagger
        AndroidSupportInjection.inject(this)
        //configureViewModel
        viewModel = ViewModelProviders.of(this, profileViewModelFactory).get(ProfileViewModel::class.java)
        viewModel.user.observe(this, Observer { updateUi() })
        updateUi()
        setFab()
    }

    private fun updateUi() {
        tvEmail.text = viewModel.user.value!!.email
        tvName.text = viewModel.user.value!!.name
        tvDescription.text = viewModel.user.value!!.description
        iv_profile_pic.setImageBitmap(viewModel.user.value!!.picture)
    }

    private fun setFab() {
        fab.setOnClickListener({
            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePicture, 0)//zero can be replaced with any action code

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            0 -> {
                if (resultCode === RESULT_OK) {
                    viewModel.setImage(data!!.extras.get("data") as Bitmap)
                }
            }
        }
    }
}