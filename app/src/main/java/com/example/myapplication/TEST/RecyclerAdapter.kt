package com.example.myapplication.TEST

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Models.queryListResponse
import com.example.myapplication.Models.queryResponse
import com.example.myapplication.R
import kotlinx.android.synthetic.main.recyclerlayout.view.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var items: List<queryResponse> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BlogViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerlayout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {

            is BlogViewHolder -> {
                holder.bind(items.get(position))
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(blogList: List<queryResponse>){
        items = blogList
        notifyDataSetChanged()
    }

    class BlogViewHolder
    constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){


        val initial_text = itemView.initialTextRecy
        val language = itemView.languageRecy
        val translated_text = itemView.translatedTextRecy
        val date_created = itemView.dateCreatedRecy

        fun bind(blogPost: queryResponse){


            initial_text.setText(blogPost.initialText)
            language.setText(blogPost.language)
            translated_text.setText(blogPost.translatedText)
            date_created.setText(blogPost.date_created)

        }

    }

}