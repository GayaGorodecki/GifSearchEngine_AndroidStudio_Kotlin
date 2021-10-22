package com.example.gifsearchengine.activity

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.gifsearchengine.R
import com.example.gifsearchengine.view.CategoriesFragment
import com.example.gifsearchengine.view.SearchFragment

class MainActivity : AppCompatActivity() {

    lateinit var fragmentTransaction: FragmentTransaction
    lateinit var fragmentManager: FragmentManager
    private var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (player == null) {
            player = MediaPlayer.create(this, R.raw.music)
            player!!.isLooping = true
        }
        player!!.start()

        fragmentManager = supportFragmentManager

        fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentCon, SearchFragment()).commit()

        var sound: ImageButton = findViewById(R.id.buttonMusic)
        sound.setOnClickListener(View.OnClickListener {
            if(player!!.isPlaying) {
                player!!.pause()
                sound.setImageResource(R.drawable.music_stop)
            }
            else {
                player!!.start()
                sound.setImageResource(R.drawable.music_start)
            }
        })

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

    override fun onDestroy() {
        super.onDestroy()
        if (player != null) {
            player!!.stop()
            player!!.release()
            player = null
        }
    }
}