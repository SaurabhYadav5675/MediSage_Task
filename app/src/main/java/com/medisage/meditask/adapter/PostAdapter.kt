package com.medisage.meditask.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.medisage.meditask.R
import com.medisage.meditask.model.Posts

class PostAdapter(private val postList: List<Posts>) : BaseAdapter() {

    override fun getCount(): Int {
        return postList.size
    }

    override fun getItem(p0: Int): Any {
        return postList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val postDetails = getItem(p0) as Posts
        val itemView = LayoutInflater.from(p2?.context).inflate(R.layout.adapter_posts, p2, false)

        val title: TextView = itemView.findViewById(R.id.postTitle)
        val body: TextView = itemView.findViewById(R.id.postBody)
        val imgFav: ImageView = itemView.findViewById(R.id.imgFav)
        title.text = Html.fromHtml("<b>Title: </b>" + postDetails.title)
        body.text = Html.fromHtml("<b>Body: </b>" + postDetails.body)

        if (postDetails.favourite == "1")
            imgFav.setBackgroundResource(R.drawable.ic_star_filled)
        else
            imgFav.setBackgroundResource(R.drawable.ic_star_border)
        return itemView
    }
}