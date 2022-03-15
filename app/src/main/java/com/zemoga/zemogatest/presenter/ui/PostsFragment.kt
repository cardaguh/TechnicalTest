package com.zemoga.zemogatest.presenter.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zemoga.zemogatest.R
import com.zemoga.zemogatest.databinding.FragmentPostsBinding
import com.zemoga.zemogatest.presenter.adapter.PostAdapter
import com.zemoga.zemogatest.presenter.viewmodel.MainViewModel
import com.zemoga.zemogatest.utils.ScreenState

class PostsFragment : Fragment() , OnCustomClickListener {

    // BINDING
    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    private lateinit var characterAdapter: PostAdapter
    private var justLlauncehed = true

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
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setViewsOnListener()
        mainViewModel.getCachedPost()



    }

    private fun setViewsOnListener(){
        with(binding.fabDelete){
            setOnClickListener {
                mainViewModel.deleteAllPosts()
            }
        }
    }

    private fun setupRecyclerView() {
        //Recibe interfaz y el otro recibe función

        //Primero pasamos interfaz y el segundo paramatro es una función
        characterAdapter = PostAdapter(viewLifecycleOwner,mainViewModel,this, ::onCategoryClickListener)

        with(binding.rvCharacterss) {
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
        this@PostsFragment.context?.let {
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
        this@PostsFragment.context?.let {
            when (renderState) {
                is PostState.ShowPostData -> {
                    characterAdapter.setCharacterData(renderState.response)
                    if(mainViewModel.characterFullList!!.isEmpty() && justLlauncehed){
                        mainViewModel.fetchPostData()
                        justLlauncehed = false
                    }else{
                        justLlauncehed = false
                    }
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

    //FORMAS DE USAR EL CLICK LISTENES

    //1 Con Interfaces
    // Crear interface e implementar funciones
    override fun showCategoryDetail(img: String) {
        Toast.makeText(context, "Mi imagen click1 $img", Toast.LENGTH_SHORT).show()
        // Aqui haces la funcionalidad de abrir el detalle

        //openDetail()
    }

    //2 Con una función (Listener)
    //No tienes que estar creando interfaces
    private fun onCategoryClickListener(title: String, body: String, id: Long,userID: Long) {
        openDetail(title, body, id, userID)

     //   Toast.makeText(context, "Mi imagen click2 $img", Toast.LENGTH_SHORT).show()
    }


    private fun openDetail(title: String, body: String, id: Long, userID: Long) {
        val action = PostsFragmentDirections.actionPostsFragmentToDetailFragment(title,body,id,userID)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.post_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_refresh -> {
                mainViewModel.fetchPostData()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}