package com.zemoga.zemogatest.presenter.ui

import android.os.Bundle
import android.view.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zemoga.zemogatest.R
import com.zemoga.zemogatest.databinding.FragmentDetailBinding
import com.zemoga.zemogatest.presenter.adapter.CommentAdapter
import com.zemoga.zemogatest.presenter.viewmodel.DetailViewModel
import com.zemoga.zemogatest.utils.ScreenState

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var commentAdapter: CommentAdapter

    private lateinit var mMenu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        detailViewModel.getState().observe(this) { onChanged(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            textViewTitle.text = args.title
            textViewBody.text = args.body
        }
        setupRecyclerView()
        setViewsOnListener()
        detailViewModel.fetchComments(args.id)
        detailViewModel.isFavorite(args.id)

    }

    private fun setViewsOnListener(){
        with(binding.fabDelete){
            setOnClickListener {
                detailViewModel.deletePost(args.id)
                requireActivity().onBackPressed()
            }
        }
    }

    private fun setupRecyclerView() {
        commentAdapter = CommentAdapter()

        with(binding.recyclerViewComments) {
            layoutManager = LinearLayoutManager(binding.root.context)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
                )
            )
            adapter = commentAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onChanged(screenState: ScreenState<PostState>?) {
        this@DetailFragment.context?.let {
            screenState?.let {
                if (screenState is ScreenState.Render) {
                    updateUI(screenState.renderState)
                } else {
                    when (screenState) {
                        ScreenState.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                        ScreenState.InternetError -> {
                            binding.progressBar.isVisible = false

                        }

                        ScreenState.ErrorServer -> {
                            binding.progressBar.isVisible = false
                            // show error
                        }
                        else -> {
                            binding.progressBar.isVisible = false

                        }
                    }
                }
            }
        }
    }

    private fun updateUI(renderState: Any) {
        this@DetailFragment.context?.let {
            when (renderState) {
                is PostState.ShowCommentsData -> {
                    commentAdapter.setCommentData(renderState.response)
                    binding.progressBar.isVisible = false

                }

                is PostState.highlightIfFavorite -> {
                    if(renderState.isFavorite){
                        mMenu.getItem(0).icon = ResourcesCompat.getDrawable(resources,R.drawable.ic_baseline_star_24, null)
                        mMenu.getItem(0).title = "Fav"
                    }
                }

                else -> {
                    binding.progressBar.isVisible = false
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_menu, menu)
        mMenu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_favorite -> {
                if(item.title == "Fav" ){
                    detailViewModel.removeFromFavorite(args.id)
                    item.icon = ResourcesCompat.getDrawable(resources,R.drawable.ic_baseline_star_border_24, null)
                }else{
                    detailViewModel.addPostToFavorite(args.id,args.userId,args.title,args.body)
                    item.icon = ResourcesCompat.getDrawable(resources,R.drawable.ic_baseline_star_24, null)
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}