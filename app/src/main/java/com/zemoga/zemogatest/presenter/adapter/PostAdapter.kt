package com.zemoga.zemogatest.presenter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zemoga.zemogatest.R
import com.zemoga.zemogatest.data.model.PostDataModel
import com.zemoga.zemogatest.data.model.PostResponse
import com.zemoga.zemogatest.presenter.ui.OnCustomClickListener
import com.zemoga.zemogatest.presenter.viewmodel.MainViewModel

class PostAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val mainViewModel: MainViewModel,
    private val onCustomClickListener1: OnCustomClickListener,
    private val onCustomClickListener2: (tile: String, body: String, id: Long, userID: Long) -> Unit

): RecyclerView.Adapter<PostAdapter.MyHolder>() {

    private var allCharacter: MutableList<PostDataModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.post_row, parent, false)
        return MyHolder(layoutView)
    }


    fun setCharacterData(response: List<PostDataModel>) {
        allCharacter.clear()
        allCharacter.addAll(response)
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val character = allCharacter[position]

        //Permite acceder a los componentes sin tener que estar escribiendo
        // category.name o category.img
        with(character) {
            //Glide.with(holder.itemView.context).load(image).into(holder.imgRestaurant)
            holder.txtResturantTitle.text = title
        }

        holder.itemView.setOnClickListener {
            //onCustomClickListener1.showCategoryDetail(category.img)
            onCustomClickListener2(character.title, character.body, character.id, character.userId)
        }

        mainViewModel.isFavorite(character.id).observe(lifecycleOwner){isFavorite ->
            if(isFavorite){
                holder.imageViewFav.visibility = View.VISIBLE
                holder.setIsRecyclable(false)
            }
        }


    }

    override fun getItemCount(): Int {
        return allCharacter.size
    }


    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtResturantTitle: TextView = itemView.findViewById(R.id.txt_name)
        val imageViewFav: ImageView = itemView.findViewById(R.id.image_btn_fav)
    }


}