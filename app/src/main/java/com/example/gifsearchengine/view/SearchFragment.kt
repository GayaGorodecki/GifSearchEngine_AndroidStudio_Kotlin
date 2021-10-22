package com.example.gifsearchengine.view

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gifsearchengine.*
import com.example.gifsearchengine.activity.MainActivity
import com.example.gifsearchengine.adapter.GifsCustomAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

lateinit var recyclerView: RecyclerView
lateinit var mainActivity: MainActivity

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_search, container, false)
        mainActivity = activity as MainActivity

        recyclerView = view.findViewById(R.id.gifImagesRecyclerView)
        var phraseText : EditText = view.findViewById(R.id.editTextPhrase)

        setRecyclerView(recyclerView, phraseText.text.toString())

        val buttonSearch: Button = view.findViewById(R.id.buttonSearch)
        buttonSearch.setOnClickListener(View.OnClickListener { view ->
            val phrase: String = phraseText.text.toString()
            loadGifs(recyclerView, phrase)
            hideKeyboard(mainActivity)
        })

        return view
    }

    fun hideKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun setRecyclerView(recyclerView: RecyclerView, phrase: String) {
        val gifList: GifList? = loadGifs(recyclerView, phrase)
        val adapter = gifList?.let { GifsCustomAdapter(it, mainActivity) }

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            recyclerView.layoutManager = GridLayoutManager(mainActivity, 5)
        } else {
            // In portrait
            recyclerView.layoutManager = GridLayoutManager(mainActivity, 3)
        }

        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    private fun loadGifs(recyclerView: RecyclerView, phrase: String) : GifList? {
        val service = ApiService.buildService(Service::class.java)

        val requestCall = if (phrase == ("")) {
            service.getTrendingGifs()
        } else {
            service.getGifsBySearch(phrase)
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
        mainActivity = activity as MainActivity
        var phraseText : EditText = mainActivity.findViewById(R.id.editTextPhrase)
        setRecyclerView(recyclerView, phraseText.text.toString())
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mainActivity = activity as MainActivity
        var phraseText : EditText = mainActivity.findViewById(R.id.editTextPhrase)
        setRecyclerView(recyclerView, phraseText.text.toString())
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