package com.toxicbakery.androidthings.mirror.ui

import android.annotation.SuppressLint
import android.os.Bundle
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import com.toxicbakery.androidthings.mirror.R

class MainActivity : KodeinAppCompatActivity() {

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}