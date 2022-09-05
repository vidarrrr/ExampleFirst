package com.examplefirst

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.examplefirst.databinding.ActivityMain2Binding
import com.squareup.picasso.Picasso
import java.lang.Exception

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main2)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        //https://developer.android.com/reference/android/app/Activity#postponeEnterTransition()
        //This method gives the Activity the ability to delay starting the entering and shared element transitions until all data is loaded.
        //Until then, the Activity won't draw into its window, leaving the window transparent.
        //This may also cause the returning animation to be delayed until data is ready.
        postponeEnterTransition()
        //supportStartPostponedEnterTransition()


        val bundle: Bundle? = intent.extras
        var id: Int? = bundle?.getInt("id")
        id = id ?: -1
        var email: String? = bundle?.getString("email")
        email = email ?: "email"
        var firstName: String? = bundle?.getString("first_name")
        firstName = firstName ?: "first name"
        var lastName: String? = bundle?.getString("last_name")
        lastName = lastName ?: "last name"
        var avatar: String? = bundle?.getString("avatar")

        val userText = "$firstName $lastName $email $id"
        binding.itemTextView.text = userText

        avatar?.let {
            Log.d("avatar", it)
        }
        if (avatar == null)
            avatar = "https://reqres.in/img/faces/7-image.jpg"

        //https://mikescamell.com/shared-element-transitions-part-4-recyclerview/
        //https://developer.android.com/develop/ui/views/animations/transitions/start-activity
        //https://stackoverflow.com/questions/55829753/picasso-callback-with-kotlin
        binding.itemImageView.transitionName = firstName
        Picasso.get().load(avatar)
            .into(binding.itemImageView, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                    Log.d("success", "success")
                    //https://developer.android.com/reference/android/app/Activity#startPostponedEnterTransition()
                    //Begin postponed transitions after postponeEnterTransition() was called.
                    //If postponeEnterTransition() was called, you must call startPostponedEnterTransition() to have your Activity start drawing.
                    startPostponedEnterTransition()
                    //supportStartPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {

                    Log.d("failed", "failed")
                    startPostponedEnterTransition()
                    //supportStartPostponedEnterTransition()
                }
            })


    }
}