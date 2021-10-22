package com.example.gifsearchengine.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.gifsearchengine.Services.BackgroundMusic
import com.example.gifsearchengine.Services.Settings
import com.example.gifsearchengine.R
import com.example.gifsearchengine.view.CategoriesFragment
import com.example.gifsearchengine.view.SearchFragment

class MainActivity : AppCompatActivity() {

    lateinit var fragmentTransaction: FragmentTransaction
    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager = supportFragmentManager
        val intent = Intent(this@MainActivity, BackgroundMusic::class.java)
        startService(intent)

        if (Settings.firstEntryFlag == true) {
            Settings.firstEntryFlag = false
            setFragment(SearchFragment())
        }

        var buttonHome: Button = findViewById(R.id.buttonHome)
        buttonHome.setOnClickListener(View.OnClickListener {
            setFragment(SearchFragment())
        })

        var buttonCategory: Button = findViewById(R.id.buttonCategories)
        buttonCategory.setOnClickListener(View.OnClickListener {
            setFragment(CategoriesFragment())
        })
    }

    fun setFragment(fragment: Fragment) {
        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentCon, fragment).addToBackStack(null).commit()
    }
}