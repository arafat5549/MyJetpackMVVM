package com.example.myjetpackmvvm_java.data.entity

import java.io.Serializable

data class UserInfo (var admin: Boolean = false,
                     var chapterTops: List<String> = listOf(),
                     var collectIds: MutableList<String> = mutableListOf()): Serializable