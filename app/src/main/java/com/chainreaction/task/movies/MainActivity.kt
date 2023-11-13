package com.chainreaction.task.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chainreaction.task.domain.model.*
import com.chainreaction.task.domain.usecase.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}