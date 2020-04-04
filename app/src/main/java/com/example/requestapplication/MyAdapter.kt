package com.example.requestapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.requestapplication.Entities.Post

class MyAdapter(var mCtx: Context, var resources: Int, var items: ArrayList<Post>) :
    ArrayAdapter<Post>(mCtx, resources, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = LayoutInflater.from(mCtx).inflate(resources, null)

        val identifierView: TextView = view.findViewById(R.id.textview1)
        val captionView: TextView = view.findViewById(R.id.textview2)
        val imageView: ImageView = view.findViewById(R.id.imageView)

        var item = items[position]
        identifierView.text = item.identifier
        captionView.text = item.caption

        Glide.with(mCtx)
            .load(
                "https://epic.gsfc.nasa.gov/archive/natural/2015/10/31/jpg/" + item.image
                        + ".jpg"
            )
            .placeholder(R.mipmap.ic_launcher)
            .into(imageView)

        return view
    }
}