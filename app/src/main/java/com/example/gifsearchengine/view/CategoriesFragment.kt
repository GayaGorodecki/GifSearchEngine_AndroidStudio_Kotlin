package com.example.gifsearchengine.view

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gifsearchengine.ApiService
import com.example.gifsearchengine.GifList
import com.example.gifsearchengine.R
import com.example.gifsearchengine.Service
import com.example.gifsearchengine.adapter.CategoriesCustomAdapter
import com.example.gifsearchengine.adapter.GifsCustomAdapter
import com.example.gifsearchengine.model.CategoriesList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoriesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_categories, container, false)

        scaleView(view)

        recyclerView = view.findViewById(R.id.recyclerViewCategories)
        setRecyclerView(recyclerView)
        return view
    }

    fun scaleView(v: View) {
        val anim: Animation = ScaleAnimation(
            0f, 1f,  // Start and end values for the X axis scaling
            1f, 1f,  // Start and end values for the Y axis scaling
            Animation.RELATIVE_TO_SELF, 0f,  // Pivot point of X scaling
            Animation.RELATIVE_TO_SELF, 1f
        ) // Pivot point of Y scaling
        anim.fillAfter = true // Needed to keep the result of the animation
        anim.duration = 800
        v.startAnimation(anim)
    }

    private fun setRecyclerView(recyclerView: RecyclerView) {
        val categoriesList: CategoriesList? = loadCategories(recyclerView)
        val adapter = categoriesList?.let { CategoriesCustomAdapter(it, mainActivity) }

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            recyclerView.layoutManager = GridLayoutManager(mainActivity, 2)
        } else {
            // In portrait
            recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        }

        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    private fun loadCategories(recyclerView: RecyclerView) : CategoriesList? {
        val service = ApiService.buildService(Service::class.java)
        var requestCall = service.getCategories()

        var categoriesList : CategoriesList? = null
        requestCall.enqueue(object : Callback<CategoriesList> {
            override fun onResponse(call: Call<CategoriesList>, response: Response<CategoriesList>) {
                if (response.isSuccessful) {
                    categoriesList = response.body()!!
                    recyclerView.apply {
                        setHasFixedSize(true)
                        adapter = CategoriesCustomAdapter(response.body()!!, mainActivity)
                    }
                }
            }

            override fun onFailure(call: Call<CategoriesList>, t: Throwable) {
                Toast.makeText(mainActivity, "Error loading categories $t", Toast.LENGTH_SHORT).show()
            }
        })

        return categoriesList
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoriesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoriesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}