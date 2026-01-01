package com.my.projekakhir.models

data class Car(
    var key: String? = null, // ID unik Firebase
    var brand: String? = "",
    var model: String? = "",
    var year: String? = "",
    var plateNumber: String? = "",
    var imageName: String? = ""
)
