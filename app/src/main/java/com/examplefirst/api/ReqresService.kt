package com.examplefirst.api

import com.examplefirst.model.UserFromService
import com.examplefirst.model.OneUserFromService
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//https://reqres.in/api/users?page=2
interface ReqresService {
    @GET("api/users")
    suspend fun getList(@Query("page") count: Int) : Response<UserFromService>

    @GET("api/users/{id}")
    suspend fun getUser(@Path("id") id : Int) : Response<OneUserFromService>

}