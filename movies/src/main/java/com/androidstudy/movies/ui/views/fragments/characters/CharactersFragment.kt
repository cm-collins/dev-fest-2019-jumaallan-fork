package com.androidstudy.movies.ui.views.fragments.characters

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.androidstudy.devfest19.core.livedata.nonNull
import com.androidstudy.devfest19.core.livedata.observe
import com.androidstudy.movies.R
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.ViewModelProvider
import com.androidstudy.movies.ui.adapter.CharactersAdapter
import com.androidstudy.movies.ui.viewmodel.CharacterViewModel
import com.androidstudy.movies.utils.SessionManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class CharactersFragment : Fragment(R.layout.fragment_characters), KoinComponent {
    private lateinit var sessionManager: SessionManager
    private val characterViewModel: CharacterViewModel by lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return get<CharacterViewModel>() as T
            }
        })[CharacterViewModel::class.java]
    }
    private lateinit var charactersAdapter: CharactersAdapter
    private lateinit var recyclerViewMovies: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewMovies = view.findViewById(R.id.recyclerViewMovies)
        sessionManager = SessionManager(requireContext())

        //check if its user's first time
        if (!sessionManager.isFirstTime()) {
            characterViewModel.getCharacters()
        } else {
            characterViewModel.fetchLocalCharacters().nonNull().observe(this) {
                charactersAdapter.updateList(it)
            }
        }

        observeLiveData()

        charactersAdapter = CharactersAdapter(emptyList()) {
            val characterFragmentAction =
                CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailsFragment(it)
            findNavController().navigate(characterFragmentAction)
        }

        recyclerViewMovies.adapter = charactersAdapter
    }

    private fun observeLiveData() {
        characterViewModel.getCharactersResponse().nonNull().observe(this) {
            if (it.results.isNotEmpty()) {
                sessionManager.setNotFirstTime(true)
                charactersAdapter.updateList(it.results)
            }
        }
        characterViewModel.getCharactersError().nonNull().observe(this) {
            sessionManager.setNotFirstTime(false)
        }
    }

}