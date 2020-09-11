package com.example.supersub.Models

data class Exercise(val _id:String?,
                    val title: String?,
                    val subtitle:String?,
                    val difficulty:String?,
                    val duration:Int?,
                    val sets:Int?,
                    val reps:Int?,
                    val description:String?,
                    val category:String?,
                    val illustration: Illustration,
                    val video: Video)

data class Video(val _id:String?,
                 val url: String)

data class Illustration(val caption:String?,
                        val imageUrl:String?,
                        val description:String?)