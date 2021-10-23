package com.example.gifsearchengine.adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gifsearchengine.GifList
import com.example.gifsearchengine.R
import kotlinx.android.synthetic.main.fragment_full_screen_gif.view.*


class GifsCustomAdapter(private val values: GifList, private val context: Context) :
    RecyclerView.Adapter<GifsCustomAdapter.MyViewHolder>() {

    override fun getItemCount() = values.gifs.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.gif_card, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context)
            .load(values.gifs[position].images.fixed_height.url)
            .asGif()
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.gifImageView)

        var path : String = values.gifs[position].images.fixed_height.url
        holder.gifImageView?.setOnClickListener(View.OnClickListener {

            showFullScreenGif(values.gifs[position].images.fixed_height.url)
        })
    }

    private fun showFullScreenGif(gifUrl: String?){
        val inflater = LayoutInflater.from(context)
        val fullScreenLayout = inflater.inflate(R.layout.fragment_full_screen_gif, null)
        val gifImageView = fullScreenLayout.imageViewFullGif

        Glide.with(context)
            .load(gifUrl)
            .asGif()
            .error(R.drawable.gif)
            .into(gifImageView)

        AlertDialog.Builder(context).run {
            val fade_in = ScaleAnimation(0f,1f,0f,1f,
                Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f)
            fade_in.duration = 800
            fade_in.fillAfter = true

            setView(fullScreenLayout)
            fullScreenLayout.startAnimation(fade_in)
            show()
        }.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var gifImageView: ImageView? = null
        init {
            gifImageView = itemView.findViewById(R.id.gifImageView)
        }
    }
}