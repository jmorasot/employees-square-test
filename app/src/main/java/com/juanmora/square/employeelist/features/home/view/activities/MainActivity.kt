package com.juanmora.square.employeelist.features.home.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.juanmora.square.employeelist.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
