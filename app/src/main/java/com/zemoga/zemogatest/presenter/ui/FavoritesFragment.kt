package com.zemoga.zemogatest.presenter.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zemoga.zemogatest.databinding.FragmentFavoriteBinding
import com.zemoga.zemogatest.presenter.adapter.PostAdapter
import com.zemoga.zemogatest.presenter.viewmodel.MainViewModel
import com.zemoga.zemogatest.utils.ScreenState

class FavoritesFragment : Fragment() , OnCustomClickListener {

    // BINDING
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    private lateinit var characterAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getState().observe(this, Observer { onChanged(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setViewsOnListener()
        mainViewModel.getFavorites()



    }

    private fun setViewsOnListener(){
        with(binding.fabDelete){
            setOnClickListener {
                mainViewModel.deleteAllPosts()
            }
        }
    }

    private fun setupRecyclerView() {
        characterAdapter = PostAdapter(viewLifecycleOwner,mainViewModel,this, ::onCategoryClickListener)

        with(binding.recyclerViewFavorites) {
            layoutManager = LinearLayoutManager(binding.root.context)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = characterAdapter
        }
    }

    /**
     * Validate answers
     * @param screenState screenState type to validate
     */
    private fun onChanged(screenState: ScreenState<PostState>?) {
        this@FavoritesFragment.context?.let {
            screenState?.let {
                if (screenState is ScreenState.Render) {
                    updateUI(screenState.renderState)
                } else {
                    when (screenState) {
                        ScreenState.Loading -> {
                            binding.progress.isVisible = true
                        }
                        ScreenState.InternetError -> {
                            binding.progress.isVisible = false

                        }

                        ScreenState.ErrorServer -> {
                            binding.progress.isVisible = false
                            // show error
                        }
                        else -> {
                            binding.progress.isVisible = false

                        }
                    }
                }
            }
        }
    }

    /**
     * Validate different kind of response
     * @param renderState type de renderState to validate
     */
    private fun updateUI(renderState: Any) {
        this@FavoritesFragment.context?.let {
            when (renderState) {
                is PostState.ShowPostData -> {
                    characterAdapter.setCharacterData(renderState.response)
                    binding.progress.isVisible = false

                }

                else -> {
                    binding.progress.isVisible = false
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showCategoryDetail(img: String) {
        Toast.makeText(context, "Mi imagen click1 $img", Toast.LENGTH_SHORT).show()

    }


    private fun onCategoryClickListener(title: String, body: String, id: Long,userID: Long) {
        openDetail(title, body, id, userID)

    }


    private fun openDetail(title: String, body: String, id: Long, userID: Long) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(title,body,id,userID)
        findNavController().navigate(action)
    }

}