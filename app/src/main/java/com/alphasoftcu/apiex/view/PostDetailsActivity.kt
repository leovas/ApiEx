package com.alphasoftcu.apiex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alphasoftcu.apiex.R
import com.alphasoftcu.apiex.databinding.ActivityPostDetailsBinding
import com.alphasoftcu.apiex.model.CommentModel
import com.alphasoftcu.apiex.view.adapters.CommentsAdapter
import com.alphasoftcu.apiex.viewmodel.PostCommentViewModel

/**
 * Activity to show detailed information for one Post
 */
class PostDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostDetailsBinding
    private lateinit var vmPostComments: PostCommentViewModel
    private lateinit var commentsAdapter: CommentsAdapter
    private var currentPostId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.post_details_activity_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //Obtaining Post ID from intent's data
        val bundle: Bundle? = intent.extras
        bundle?.let { currentPostId = it.getInt(POST_ID) }

        initAdapterRecycler()
        initViewModel()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Initialize Comment's Adapter and RecyclerView
     */
    private fun initAdapterRecycler() {
        commentsAdapter = CommentsAdapter()
        binding.rvPostsDetailsComments.layoutManager = LinearLayoutManager(this)
        binding.rvPostsDetailsComments.adapter = commentsAdapter
    }

    /**
     * Initialize ViewModel container of data to show in this UI.
     * Make the binding with the UI's elements
     */
    private fun initViewModel() {
        vmPostComments = ViewModelProvider(this)[PostCommentViewModel::class.java]
        currentPostId?.let { vmPostComments.getPost(it) }
        vmPostComments.postModelLiveData?.observe(this, {
            it?.let {
                binding.tvPostDetailsTitle.text = it.title
                binding.tvPostsDetailsBody.text = it.body

                it.userId?.let { userId ->
                    vmPostComments.getUser(userId)
                    vmPostComments.userModelLiveData?.observe(this, { user ->
                        binding.tvPostDetailsName.text = "${user?.name} <${user?.email}>"
                    })
                }
                it.id?.let { postId ->
                    vmPostComments.getComments(postId)
                    vmPostComments.commentModelLiveData?.observe(this, { commentsList ->
                        commentsAdapter.setData(commentsList as ArrayList<CommentModel>)
                    })
                }
            }
            binding.pbContactDetailConnect.visibility = View.GONE
        })
    }
}