package com.examplefirst.model

import com.google.gson.annotations.SerializedName

//https://www.workversatile.com/json-to-kotlin-converter
data class UserFromService (

    @SerializedName("page") var page : Int,
    @SerializedName("per_page") var perPage : Int,
    @SerializedName("total") var total : Int,
    @SerializedName("total_pages") var totalPages : Int,
    @SerializedName("data") var data : List<Data>,
    @SerializedName("support") var support : Support

)



