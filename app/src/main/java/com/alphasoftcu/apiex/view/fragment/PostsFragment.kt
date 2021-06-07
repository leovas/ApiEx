package com.alphasoftcu.apiex.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alphasoftcu.apiex.databinding.FragmentPostsBinding
import com.alphasoftcu.apiex.model.PostModel
import com.alphasoftcu.apiex.view.POST_ID
import com.alphasoftcu.apiex.view.PostDetailsActivity
import com.alphasoftcu.apiex.view.adapters.PostsAdapter
import com.alphasoftcu.apiex.viewmodel.PostsViewModel


/**
 * A simple [Fragment] subclass. Handle view for Posts list
 */
class PostsFragment : Fragment() {
    private lateinit var postsViewModel: PostsViewModel
    private var _binding: FragmentPostsBinding? = null
    private lateinit var postAdapter: PostsAdapter

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postsViewModel = ViewModelProvider(this)[PostsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        val root = binding.root

        //Initialize ViewModel for this UI, bind UI's components with data source
        postsViewModel.getAllPosts()
        initAdapterRecycler()
        postsViewModel.postModelListLiveData?.observe(viewLifecycleOwner, {
            it?.let {
                postAdapter.setData(it as ArrayList<PostModel>)
                binding.pbPostsLoad.visibility = View.GONE
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Initialize Post's Adapter and RecyclerView
     */
    private fun initAdapterRecycler(){
        postAdapter = PostsAdapter(onPostItemClick)
        binding.rvPosts.layoutManager = LinearLayoutManager(context)
        binding.rvPosts.adapter = postAdapter
    }

    /**
     * Post's items onClick function to open post's details Activity
     */
    private val onPostItemClick = { postModel: PostModel ->
        val intent = Intent(context, PostDetailsActivity::class.java)
        intent.putExtra(POST_ID, postModel.id)
        startActivity(intent)
    }
}