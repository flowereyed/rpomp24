package com.example.requestapplication.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.requestapplication.Entities.Post
import com.example.requestapplication.R

/**
 * A simple [Fragment] subclass.
 */
class DescriptionFragment : Fragment() {

    private var image: ImageView? = null
    private var identifier: TextView? = null
    private var caption: TextView? = null
    private var lat: TextView? = null
    private var lon: TextView? = null
    private var button: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_description, container, false)
        image = view.findViewById(R.id.imageView)
        identifier = view.findViewById(R.id.textview1)
        caption = view.findViewById(R.id.textview2)
        lat = view.findViewById(R.id.lat)
        lon = view.findViewById(R.id.lon)
        button = view.findViewById(R.id.backButton)

        val post: Post = arguments!!.getParcelable("post")!!
        Glide.with(view.context)
            .load(
                "https://epic.gsfc.nasa.gov/archive/natural/2015/10/31/jpg/" + post.image
                        + ".jpg"
            )
            .placeholder(R.mipmap.ic_launcher)
            .into(image!!)

        identifier!!.text = post.identifier
        caption!!.text = post.caption
        lat!!.text = post.centroidCoordinates.lat
        lon!!.text = post.centroidCoordinates.lon

        button!!.setOnClickListener {
            showListFragment()
        }
        return view
    }

    private fun showListFragment() {
        val manager = activity!!.supportFragmentManager
        manager.popBackStack()
    }
}