package com.example.testapp1.feature.savedNewsFragment.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp1.R
import com.example.testapp1.data.local.model.ArticleEntity
import com.example.testapp1.databinding.FragmentSavedNewsBinding
import com.example.testapp1.feature.savedNewsFragment.presentation.SavedNewsViewModel
import com.example.testapp1.utils.baseClasses.BaseFragment
import com.example.testapp1.utils.visibilityIf
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_saved_news.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SavedNewsFragment :
    BaseFragment<FragmentSavedNewsBinding>(FragmentSavedNewsBinding::inflate) {

    private val savedNewsViewModel: SavedNewsViewModel by viewModel()

    private val newsAdapter by lazy { SavedNewsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initTouchListener()

        newsAdapter.setOnItemClickListener {
            navigate(it)
        }

        savedNewsViewModel.getSavedNews().observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
            if (it.isNotEmpty()) changeVisibilityIfNoArticles(true)
            if (it.isEmpty()) changeVisibilityIfNoArticles(false)
        }
    }

    private fun initTouchListener() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.currentList[position]
                savedNewsViewModel.deleteArticle(article)
                Snackbar.make(
                    requireView(),
                    getString(R.string.successfully_deleted_article),
                    Snackbar.LENGTH_LONG
                )
                    .apply {
                        setAction(getString(R.string.undo)) {
                            savedNewsViewModel.reloadArticle(article)
                        }
                        show()
                    }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvSavedNews)
        }
    }

    private fun navigate(articleEntity: ArticleEntity) {
        findNavController().navigate(
            SavedNewsFragmentDirections
                .actionSavedNewsFragmentToArticleFragment(
                    null,
                    articleEntity
                )
        )
    }

    private fun initRecyclerView() {
        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun changeVisibilityIfNoArticles(hasArticles: Boolean) {
        with(binding) {
            rvSavedNews.visibilityIf(hasArticles)
            noSavedArticlesImageView.visibilityIf(!hasArticles)
            noSavedArticlesTitleTextView.visibilityIf(!hasArticles)
            noSavedArticlesDescriptionTextView.visibilityIf(!hasArticles)
        }
    }
}