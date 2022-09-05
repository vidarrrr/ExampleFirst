package com.examplefirst

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.examplefirst.api.ReqresService
import com.examplefirst.model.User
import com.examplefirst.recyclerAdapter.UserAdapter
import com.examplefirst.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //next -> https://developer.android.com/topic/libraries/architecture/coroutines
    //https://developer.android.com/kotlin/coroutines/coroutines-best-practices
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        //https://www.geeksforgeeks.org/android-recyclerview-in-kotlin/
        val users: ArrayList<User> = ArrayList<User>()
        users.add(User(1,"marie@gmail.com","Marie","Marie","https://reqres.in/img/faces/7-image.jpg"))

        val userAdapter = UserAdapter { user,imageView -> adapterOnClick(user,imageView) }

        binding.recyclerView.adapter = userAdapter



        //userAdapter.submitList(users)

        val usersFromService = RetrofitHelper.getInstance().create(ReqresService::class.java)

        binding.buttonFirst.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                val result = usersFromService.getUser(3)
                result.body()?.let {
                    val data1 = it.data
                    users.add(User(data1.id,data1.email,data1.firstName,data1.lastName,data1.avatar))
                    runOnUiThread{
                        userAdapter.submitList(users)
                        userAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
        //https://www.geeksforgeeks.org/retrofit-with-kotlin-coroutine-in-android/
        //https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/
        //https://medium.com/swlh/how-can-we-use-coroutinescopes-in-kotlin-2210695f0e89
        //https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-global-scope/
        //https://elizarov.medium.com/the-reason-to-avoid-globalscope-835337445abc
        //https://developer.android.com/kotlin/coroutines/coroutines-best-practices
        //https://stackoverflow.com/questions/54335365/why-not-use-globalscope-launch

        //Global scope is used to launch top-level coroutines which are operating on the whole application lifetime and are not cancelled prematurely
        /*GlobalScope.launch {
            val result = usersFromService.getList(1)
            result.body()?.let {

                for (datum in it.data){
                    users.add(User(datum.id,datum.email,datum.firstName,datum.lastName,datum.avatar))
                }

                runOnUiThread {
                    userAdapter.submitList(users)
                    userAdapter.notifyDataSetChanged()
                }

            }
        }*/

        CoroutineScope(Dispatchers.IO).launch {

            val result = usersFromService.getList(1)
            result.body()?.let {

                for (datum in it.data){
                    users.add(User(datum.id,datum.email,datum.firstName,datum.lastName,datum.avatar))
                }

                runOnUiThread {
                    userAdapter.submitList(users)
                    userAdapter.notifyDataSetChanged()
                }

            }
        }

    }

    //https://stackoverflow.com/questions/55946662/how-to-used-a-shared-transition-from-recyclerview-to-a-fragment
    //https://mikescamell.com/shared-element-transitions-part-4-recyclerview/
    //https://developer.android.com/develop/ui/views/animations/transitions/start-activity
    private fun adapterOnClick(user: User, imageView: ImageView){
        val intent = Intent(this,MainActivity2()::class.java)
        intent.putExtra("id",user.id)
        intent.putExtra("email",user.email)
        intent.putExtra("first_name",user.firstName)
        intent.putExtra("last_name",user.lastName)
        intent.putExtra("avatar",user.avatar)
        val options = ActivityOptions.makeSceneTransitionAnimation(this,imageView, ViewCompat.getTransitionName(imageView))
        startActivity(intent,options.toBundle())
    }



    //https://www.geeksforgeeks.org/retrofit-with-kotlin-coroutine-in-android/
    object RetrofitHelper{
        private const val baseURL = "https://reqres.in/";

        fun getInstance(): Retrofit {
            return Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}

//https://github.com/square/retrofit/blob/master/retrofit/src/main/resources/META-INF/proguard/retrofit2.pro
//https://github.com/square/okio/blob/master/okio/src/jvmMain/resources/META-INF/proguard/okio.pro
//https://raw.githubusercontent.com/square/okhttp/master/okhttp/src/jvmMain/resources/META-INF/proguard/okhttp3.pro
//https://square.github.io/retrofit/
//https://reqres.in/
//https://jsonplaceholder.typicode.com/
//https://github.com/android/views-widgets-samples/blob/main/RecyclerViewKotlin/app/src/main/java/com/example/recyclersample/data/DataSource.kt
//https://github.com/android/views-widgets-samples/blob/main/RecyclerViewKotlin/app/src/main/java/com/example/recyclersample/flowerList/HeaderAdapter.kt
//https://github.com/android/views-widgets-samples/blob/main/RecyclerViewKotlin/app/src/main/java/com/example/recyclersample/flowerList/FlowersListViewModel.kt
//https://github.com/android/views-widgets-samples/blob/main/RecyclerViewKotlin/app/src/main/java/com/example/recyclersample/flowerList/FlowersListActivity.kt
//https://github.com/android/views-widgets-samples/blob/main/RecyclerViewKotlin/app/src/main/java/com/example/recyclersample/flowerList/FlowersAdapter.kt
//https://developer.android.com/develop/ui/views/layout/recyclerview