package com.example.gifsearchengine.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gifsearchengine.GifList
import com.example.gifsearchengine.R
import com.example.gifsearchengine.view.FullScreenGifFragment
import com.example.gifsearchengine.view.mainActivity

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
            .error(R.drawable.error_page)
            .into(holder.gifImageView)

        var path : String = values.gifs[position].images.fixed_height.url
        holder.gifImageView?.setOnClickListener(View.OnClickListener { View ->
            mainActivity.setFragment(FullScreenGifFragment.newInstance(path))
        })
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var gifImageView: ImageView? = null

        init {
            gifImageView = itemView.findViewById(R.id.gifImageView)
        }
    }
}