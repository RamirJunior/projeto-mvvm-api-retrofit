package com.ramirjr.mvvmproject

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ramirjr.mvvmproject.adapters.MainAdapter
import com.ramirjr.mvvmproject.databinding.ActivityMainBinding
import com.ramirjr.mvvmproject.repositories.MainRepository
import com.ramirjr.mvvmproject.res.RetrofitService
import com.ramirjr.mvvmproject.viewmodel.main.MainViewModel
import com.ramirjr.mvvmproject.viewmodel.main.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    lateinit var viewModel: MainViewModel

    private val retrofitService = RetrofitService.getInstance()

    private val adapter = MainAdapter {
        //TODO abrir o link
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(
                MainRepository(retrofitService)
            )
        ).get(MainViewModel::class.java)

        binding.recyclerview.adapter = adapter

    }

    override fun onStart() {
        super.onStart()

        viewModel.liveList.observe(this, Observer { lives ->
            Log.i("MainActivity","onStart")
            adapter.setLiveList(lives)
        })

        viewModel.errorMessage.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllLives()
    }
}