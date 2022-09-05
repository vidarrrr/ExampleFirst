package com.examplefirst.recyclerAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.examplefirst.model.User
import com.examplefirst.R

import com.squareup.picasso.Picasso

//https://github.com/android/views-widgets-samples/blob/main/RecyclerViewKotlin/app/src/main/java/com/example/recyclersample/flowerList/FlowersAdapter.kt
//https://www.geeksforgeeks.org/android-recyclerview-in-kotlin/
class UserAdapter(private val onClick: (User, ImageView) -> Unit): ListAdapter<User, UserAdapter.UserViewModel>(
    UserDiffCallback
) {





    class UserViewModel(itemView: View, val onClick: (User, ImageView) -> Unit) : RecyclerView.ViewHolder(itemView){
        private val imageView: ImageView = itemView.findViewById(R.id.item_image_view);
        private val textView: TextView = itemView.findViewById(R.id.item_text_view);
        private var currentUser: User? = null
        init {
            itemView.setOnClickListener {
                currentUser?.let {
                    onClick(it,imageView)
                }
            }
        }

        fun bind(user: User){
            currentUser = user
            val userText : String = user.firstName + " " + user.lastName + " " + user.email + " "
            textView.text = userText
            retrieveImageFromURL(imageView,user.avatar)

        }

        fun setTransitionName(transitionName : String){
            ViewCompat.setTransitionName(imageView,transitionName)
        }

        //https://developer.android.com/codelabs/basic-android-kotlin-training-internet-images#2
        private fun retrieveImageFromURL(imageView: ImageView, url : String){
            Picasso.get().load(url).into(imageView);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewModel {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item_view,parent,false)
        return UserViewModel(view,onClick)
    }

    override fun onBindViewHolder(holder: UserViewModel, position: Int) {
        val user: User = getItem(position)
        holder.bind(user)
        holder.setTransitionName(user.firstName)
    }
}

object UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id &&
                oldItem.firstName == newItem.firstName &&
                oldItem.lastName == newItem.lastName &&
                oldItem.email == newItem.email &&
                oldItem.avatar == newItem.avatar
    }
}

