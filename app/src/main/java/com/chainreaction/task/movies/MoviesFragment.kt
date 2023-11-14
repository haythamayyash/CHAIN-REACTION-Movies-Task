package com.chainreaction.task.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.chainreaction.task.movies.databinding.FragmentMoviesBinding
import com.chainreaction.task.util.shareContent
import com.chainreaction.task.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var adapter: MoviesAdapter

    private var _viewBinding: FragmentMoviesBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentMoviesBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initListeners()
        observeChanges()
    }

    private fun setupRecyclerView() {
        adapter = MoviesAdapter()
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        viewBinding.recyclerView.adapter = adapter
    }

    private fun initListeners() {
        adapter.onShareClick = { title, overview ->
            viewModel.onShareClicked(title, overview)
        }
    }

    private fun observeChanges() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getMovies().collectLatest {
                        adapter.submitData(lifecycle, it)
                    }
                }
                launch {
                    viewModel.eventState.collect {
                        handleEvent(it)
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collect { loadState ->
                    handleLoadState(loadState)
                }
            }
        }
    }

    private fun handleEvent(event: MoviesViewModel.MoviesEvent) {
        when (event) {
            is MoviesViewModel.MoviesEvent.Share -> {
                requireContext().shareContent("${event.title} \n ${event.overview}")
            }
        }
    }

    private fun handleLoadState(loadState: CombinedLoadStates) {
        viewBinding.progressbar.isVisible = loadState.refresh is LoadState.Loading
        when {
            loadState.append is LoadState.Error ||
                    loadState.refresh is LoadState.Error ||
                    loadState.prepend is LoadState.Error -> {
                showToast(getString(R.string.error_generic_message))
            }

            loadState.prepend is LoadState.Loading -> {
                viewBinding.progressbar.isVisible = true
            }

            else -> {
                viewBinding.progressbar.isVisible = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}
