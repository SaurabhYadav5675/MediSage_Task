package com.medisage.meditask.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.medisage.meditask.HomeActivity
import com.medisage.meditask.R
import com.medisage.meditask.adapter.PostAdapter
import com.medisage.meditask.model.Posts
import com.medisage.meditask.utilities.FieldValidation
import com.medisage.meditask.viewModel.PostViewModel
import com.medisage.meditask.viewModel.PostViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostFragment : Fragment() {

    private lateinit var viewModel: PostViewModel
    private lateinit var postAdapter: PostAdapter
    private lateinit var homeActivity: HomeActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_post, container, false)
        val postListView: ListView = view.findViewById<ListView>(R.id.postListView)

        homeActivity = (activity as HomeActivity?)!!
        val repository = homeActivity.repository

        GlobalScope.launch { repository.getPosts() }
        viewModel =
            ViewModelProvider(this, PostViewModelFactory(repository)).get(PostViewModel::class.java)

        viewModel.postsLocal.observe(viewLifecycleOwner, Observer {
            if (it != null && it.isNotEmpty()) {
                val postData: List<Posts> = it
                postAdapter = PostAdapter(postData)
                postAdapter.notifyDataSetChanged()
                postListView.adapter = postAdapter
                Toast.makeText(this.homeActivity, "getting data", Toast.LENGTH_SHORT).show()
            } else {
                if (FieldValidation.isInternetAvailable(this.homeActivity)) {
                    Toast.makeText(
                        this.homeActivity,
                        "No data found",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this.homeActivity,
                        "No data available in offline mode.please check network for online",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        postListView.setOnItemClickListener { parent, view, position, id ->
            val element = parent.getItemAtPosition(position) as Posts
            val favStatus = if (element.favourite == "0") "1" else "0"

            viewModel.updatePostData(element.id, favStatus)
        }
        return view;
    }

}