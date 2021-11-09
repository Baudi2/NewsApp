package com.example.testapp1.feature.savedNewsFragment.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp1.data.local.model.ArticleEntity
import com.example.testapp1.databinding.FragmentSavedNewsBinding
import com.example.testapp1.di.feature.component.DaggerFeatureComponent
import com.example.testapp1.di.feature.module.SavedNewsFragmentModule
import com.example.testapp1.feature.savedNewsFragment.presentation.SavedNewsViewModel
import com.example.testapp1.utils.baseClasses.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_saved_news.*
import javax.inject.Inject


class SavedNewsFragment :
    BaseFragment<FragmentSavedNewsBinding>(FragmentSavedNewsBinding::inflate) {

    @Inject
    lateinit var viewModel: SavedNewsViewModel
    private val newsAdapter by lazy { SavedNewsAdapter() }

    override fun onAttach(context: Context) {
        DaggerFeatureComponent
            .builder()
            .savedNewsFragmentModule(SavedNewsFragmentModule(this))
            .build()
            .inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initTouchListener()

        newsAdapter.setOnItemClickListener {
            navigate(it)
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
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
                viewModel.deleteArticle(article)
                Snackbar.make(requireView(), "Successfully deleted article", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo") {
                            viewModel.reloadArticle(article)
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
//        findNavController().navigate(
//            SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleFragment(
//                null,
//                articleEntity
//            )
//        )
        //TODO: deal with navigation
    }

    private fun setupRecyclerView() {
        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}