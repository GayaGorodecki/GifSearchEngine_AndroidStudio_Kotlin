package com.example.gifsearchengine.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.gifsearchengine.R
import com.example.gifsearchengine.view.SearchFragment

class MainActivity : AppCompatActivity() {

    lateinit var fragmentTransaction: FragmentTransaction
    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager = supportFragmentManager

        fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentCon, SearchFragment()).commit()
    }
}