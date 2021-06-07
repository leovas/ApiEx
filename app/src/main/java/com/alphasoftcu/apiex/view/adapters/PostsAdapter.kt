package com.alphasoftcu.apiex.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alphasoftcu.apiex.R
import com.alphasoftcu.apiex.model.PostModel

/**
 * Adapter for PostModel data
 */
class PostsAdapter(private val onClick: (PostModel) -> Unit) :
    RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    private var data: ArrayList<PostModel>? = null

    /**
     * Provide the list of PostModel items
     * @param postList List of Posts (PostModel)
     */
    fun setData(postList: ArrayList<PostModel>) {
        data = postList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        return PostsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.post_item_view, parent, false),
            onClick
        )
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val postData = data?.get(position)
        holder.bind(postData)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    /**
     * HolderView that manage the item's view inside de Adapter.
     * Bind the view components with data
     * @param postView parent View container of post item UI
     * @param onClick Function called on item click event.
     *                Function's parameter type is PostModel
     */
    class PostsViewHolder(postView: View, val onClick: (PostModel) -> Unit) :
        RecyclerView.ViewHolder(postView) {
        val tvTitle: TextView = postView.findViewById(R.id.tv_post_item_title)
        val tvBody: TextView = postView.findViewById(R.id.tv_post_item_body)
        var itemPost: PostModel? = null

        init {
            postView.setOnClickListener {
                itemPost?.let { onClick(it) }
            }
        }

        fun bind(postItem: PostModel?) {
            itemPost = postItem
            tvTitle.text = postItem?.title
            tvBody.text = postItem?.body
        }
    }
}