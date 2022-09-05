package com.examplefirst.model

import com.google.gson.annotations.SerializedName

data class OneUserFromService (

    @SerializedName("data") var data : Data,
    @SerializedName("support") var support : Support

)