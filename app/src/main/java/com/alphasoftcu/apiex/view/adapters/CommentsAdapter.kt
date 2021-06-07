package com.alphasoftcu.apiex.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alphasoftcu.apiex.R
import com.alphasoftcu.apiex.model.CommentModel

/**
 * View adapter for CommentModel items
 */
class CommentsAdapter: RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    private var data: ArrayList<CommentModel> ?= null


    /**
     * Set collection of Comments data for the adapter
     * @param commnetList List of CommentModel objects
     */
    fun setData(commnetList: ArrayList<CommentModel>){
        data = commnetList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.comment_item_view,parent, false))
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val commentData: CommentModel? = data?.get(position)
        holder.bind(commentData)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    /**
     * View Holder that provide de Views for RecyclerView
     */
    class CommentViewHolder(commentView: View): RecyclerView.ViewHolder(commentView){
        val tvName: TextView = commentView.findViewById(R.id.tv_comment_item_name)
        val tvEmail: TextView = commentView.findViewById(R.id.tv_comment_item_email)
        val tvBody: TextView = commentView.findViewById(R.id.tv_comment_item_body)

        fun bind(commentItem: CommentModel?){
            tvName.text = commentItem?.name
            tvEmail.text = "<${commentItem?.email}>"
            tvBody.text = commentItem?.body
        }
    }
}