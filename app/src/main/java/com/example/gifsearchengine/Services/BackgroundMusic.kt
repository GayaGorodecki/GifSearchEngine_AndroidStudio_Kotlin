package com.example.gifsearchengine.Services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.example.gifsearchengine.R

class BackgroundMusic : Service() {

    private var player: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, R.raw.music)
        player!!.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player!!.start();
        return startId;
    }

    override fun onDestroy() {
        super.onDestroy()
        player!!.stop();
        player!!.release();
    }
}