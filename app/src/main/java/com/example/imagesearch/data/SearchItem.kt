package com.example.imagesearch.data

data class SearchItem(
    var title : String,
    var dateTime :String,
    var thumbUrl : String,
    var isLike : Boolean = false
)