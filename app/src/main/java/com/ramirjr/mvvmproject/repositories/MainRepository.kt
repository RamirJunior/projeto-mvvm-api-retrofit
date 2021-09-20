package com.ramirjr.mvvmproject.repositories

import com.ramirjr.mvvmproject.res.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService){

    fun getAllLives() = retrofitService.getAllLives()

}