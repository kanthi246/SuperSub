package com.example.supersub.Models

data class Explore(
    val banner: Banner?,
    val categories:List<Category>,
    val topPicks: TopPicks?)

data class Banner(val header:String?,val image:String)

data class Category(
    val _id:String?,
    val name: String,
    val icon:String?)

data class TopPicks(
    val title:String?,
    val drills: List<Drills>)

data class Drills(val _id:String,
                  val title: String?,
                  val subtitle:String?,
                  val difficulty:String?,
                  val duration:Int,val video: Video)
