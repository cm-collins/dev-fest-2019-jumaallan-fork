package com.androidstudy.movies.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.androidstudy.movies.R
import com.androidstudy.movies.data.remote.Character

typealias  ClickListener = (Character) -> Unit

class CharactersAdapter(
    private var charactersList: List<Character>,
    private val clickListener: ClickListener
) : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_movie_item, parent, false)
        return CharactersViewHolder(itemView, clickListener)
    }

    override fun getItemCount(): Int = charactersList.size

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bindCharacter(charactersList[position])
    }

    fun updateList(characterList: List<Character>) {
        charactersList = characterList
        notifyDataSetChanged()
    }

    class CharactersViewHolder(
        itemView: View, private val clickListener: ClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        private val imageViewCharacterImage: ImageView = itemView.findViewById(R.id.imageViewCharacterImage)
        private val textViewCharacterName: TextView = itemView.findViewById(R.id.textViewCharacterName)

        fun bindCharacter(character: Character) {
            with(character) {
                textViewCharacterName.text = name
                imageViewCharacterImage.load(image)
                itemView.setOnClickListener {
                    clickListener(character)
                }
            }
        }
    }
}