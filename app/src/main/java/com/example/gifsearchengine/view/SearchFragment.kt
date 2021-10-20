package com.example.gifsearchengine.view

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gifsearchengine.ApiService
import com.example.gifsearchengine.GifList
import com.example.gifsearchengine.R
import com.example.gifsearchengine.Service
import com.example.gifsearchengine.activity.MainActivity
import com.example.gifsearchengine.adapter.GifsCustomAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

lateinit var recyclerView: RecyclerView
lateinit var mainActivity: MainActivity

const val SHARED_PREFS_PHRASE = "PhraseSharedPrefs"
const val PHRASE = "phrase"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_search, container, false)

        mainActivity = activity as MainActivity
        recyclerView = view.findViewById(R.id.gifImagesRecyclerView)

        setRecyclerView(recyclerView, "")

        val phraseText: EditText = view.findViewById(R.id.editTextPhrase)
        val buttonSearch: Button = view.findViewById(R.id.buttonSearch)

        buttonSearch.setOnClickListener(View.OnClickListener { view ->
            val phrase : String = phraseText.text.toString()
            loadGifs(recyclerView, phrase)

            val editor: SharedPreferences.Editor = mainActivity.getSharedPreferences(
                    SHARED_PREFS_PHRASE, Context.MODE_PRIVATE).edit()
            editor.putString(PHRASE, phrase)
            editor.apply()
        })

        return view
    }

    private fun setRecyclerView(recyclerView: RecyclerView, phrase: String) {
        val gifList: GifList? = loadGifs(recyclerView, phrase)
        val adapter = gifList?.let { GifsCustomAdapter(it, mainActivity) }

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            recyclerView.layoutManager = GridLayoutManager(mainActivity, 4)
        } else {
            // In portrait
            recyclerView.layoutManager = GridLayoutManager(mainActivity, 2)
        }

        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    private fun loadGifs(recyclerView: RecyclerView, phase: String) : GifList? {
        val service = ApiService.buildService(Service::class.java)

        val requestCall = if (phase == ("")) {
            service.getTrendingGifs()
        } else {
            service.getGifsBySearch(phase)
        }

        var gifList : GifList? = null
        requestCall.enqueue(object : Callback<GifList> {
            override fun onResponse(call: Call<GifList>, response: Response<GifList>) {
                if (response.isSuccessful) {
                    gifList = response.body()!!
                    recyclerView.apply {
                        setHasFixedSize(true)
                        adapter = GifsCustomAdapter(response.body()!!, mainActivity)
                    }
                }
            }

            override fun onFailure(call: Call<GifList>, t: Throwable) {
                Toast.makeText(mainActivity, "Error loading gifs $t", Toast.LENGTH_SHORT).show()
            }
        })

        return gifList
    }

    override fun onResume() {
        super.onResume()
        val editor: SharedPreferences.Editor = mainActivity.getSharedPreferences(
                SHARED_PREFS_PHRASE, Context.MODE_PRIVATE).edit()
        editor.putString(PHRASE, "")
        editor.apply()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val prefs: SharedPreferences = mainActivity.getSharedPreferences(SHARED_PREFS_PHRASE, Context.MODE_PRIVATE)
        val phrase = prefs.getString(PHRASE, "") as String
        setRecyclerView(recyclerView, phrase)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SearchFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}