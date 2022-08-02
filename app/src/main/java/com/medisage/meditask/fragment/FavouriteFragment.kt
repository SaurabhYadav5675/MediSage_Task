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
import com.medisage.meditask.adapter.FavPostAdapter
import com.medisage.meditask.model.Posts
import com.medisage.meditask.viewModel.PostViewModel
import com.medisage.meditask.viewModel.PostViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavouriteFragment : Fragment() {

    private lateinit var viewModel: PostViewModel
    private lateinit var favAdapter: FavPostAdapter
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourite, container, false)
        val favListView: ListView = view.findViewById<ListView>(R.id.favouriteListView)

        homeActivity = (activity as HomeActivity?)!!
        val repository = homeActivity.repository
        GlobalScope.launch { repository.getFavPosts() }
        viewModel =
            ViewModelProvider(this, PostViewModelFactory(repository)).get(PostViewModel::class.java)

        viewModel.postsFavLocal.observe(viewLifecycleOwner, Observer {
            if (it != null && it.isNotEmpty()) {
                val postData: List<Posts> = it
                favAdapter = FavPostAdapter(postData)
                favAdapter.notifyDataSetChanged()
                favListView.adapter = favAdapter
            } else {
                Toast.makeText(
                    this.homeActivity,
                    "No data found in favourite list",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        return view
    }
}