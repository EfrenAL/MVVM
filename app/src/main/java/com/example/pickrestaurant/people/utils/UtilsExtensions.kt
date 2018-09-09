package com.example.pickrestaurant.people.utils

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.example.pickrestaurant.people.model.User
import kotlinx.android.synthetic.main.activity_people_details.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by efren.lamolda on 02.08.18.
 */

fun String.md5(): String {

    var m: MessageDigest? = null

    try {
        m = MessageDigest.getInstance("MD5")
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }

    m!!.update(this.toByteArray(), 0, this.length)
    return BigInteger(1, m.digest()).toString(16)
}


fun Bitmap.toFile(context: Context, filename: String): File {
    //create a file to write bitmap data
    var f = File(context.cacheDir, filename)
    f.createNewFile()

    //Convert bitmap to byte array
    var bos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos)
    var bitmapdata = bos.toByteArray()

    //write the bytes in file
    var fos = FileOutputStream(f)
    fos.write(bitmapdata)
    fos.flush()
    fos.close()
    return f
}

fun User.loadImage(context: Context, imageView: ImageView) {
    Glide.with(context)
            .load(/*BUCKET_URL +*/ this.pictureUrl)
            .asBitmap()
            .centerCrop()
            .into(object : BitmapImageViewTarget(imageView) {
                override fun setResource(resource: Bitmap) {
                    val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                    circularBitmapDrawable.isCircular = true
                    imageView.setImageDrawable(circularBitmapDrawable)
                }
            })
}