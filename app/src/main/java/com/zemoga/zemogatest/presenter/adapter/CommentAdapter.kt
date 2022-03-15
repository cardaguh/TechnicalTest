package com.zemoga.zemogatest.presenter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zemoga.zemogatest.R
import com.zemoga.zemogatest.data.model.CommentModel
import com.zemoga.zemogatest.data.model.PostDataModel
import com.zemoga.zemogatest.data.model.PostResponse
import com.zemoga.zemogatest.presenter.ui.OnCustomClickListener

class CommentAdapter(): RecyclerView.Adapter<CommentAdapter.MyHolder>() {
    private var allComments: MutableList<CommentModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.comment_row, parent, false)
        return MyHolder(layoutView)
    }


    fun setCommentData(response: List<CommentModel>) {
        allComments.clear()
        allComments.addAll(response)
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val comment = allComments[position]

        holder.textViewBody.text = comment.body


    }

    override fun getItemCount(): Int {
        return allComments.size
    }


    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewBody: TextView = itemView.findViewById(R.id.text_view_body)
    }


}