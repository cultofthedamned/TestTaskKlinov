package com.klinovvlad.testtaskklinov.Model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("data") val _data: List<DataObj>
)