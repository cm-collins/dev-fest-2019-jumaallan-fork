package com.androidstudy.movies.ui.views.fragments.characterdetails

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.androidstudy.movies.R
import androidx.lifecycle.ViewModelProvider
import com.androidstudy.movies.data.remote.Character
import com.androidstudy.movies.ui.viewmodel.CharacterViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details), KoinComponent {
    private val characterViewModel: CharacterViewModel by lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return get<CharacterViewModel>() as T
            }
        })[CharacterViewModel::class.java]
    }
    val characterDetailsSafeArgs : CharacterDetailsFragmentArgs by navArgs()

    private lateinit var bannerImgView: ImageView
    private lateinit var nameTxtView: TextView
    private lateinit var genderTxtView: TextView
    private lateinit var typeTxtView: TextView
    private lateinit var statusTxtView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bannerImgView = view.findViewById(R.id.bannerImgView)
        nameTxtView = view.findViewById(R.id.nameTxtView)
        genderTxtView = view.findViewById(R.id.genderTxtView)
        typeTxtView = view.findViewById(R.id.typeTxtView)
        statusTxtView = view.findViewById(R.id.statusTxtView)

        val character = characterDetailsSafeArgs.character
        setupViews(character)
    }

    private fun setupViews(character: Character) {
        bannerImgView.load(character.image)
        nameTxtView.text = character.name
        genderTxtView.text = character.gender
        typeTxtView.text = character.origin.name
        statusTxtView.text = character.status

    }
}