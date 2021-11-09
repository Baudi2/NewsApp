package com.example.testapp1.feature.breakingNewsFragment.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp1.data.remote.model.ArticleRemote
import com.example.testapp1.data.remote.model.NewsResponse
import com.example.testapp1.databinding.FragmentBreakingNewsBinding
import com.example.testapp1.di.feature.component.DaggerFeatureComponent
import com.example.testapp1.di.feature.module.ViewModelFactory
import com.example.testapp1.feature.breakingNewsFragment.presentation.BreakingNewsViewModel
import com.example.testapp1.feature.ui.NewsAdapter
import com.example.testapp1.utils.Constants.Companion.QUERY_PAGE_SIZE
import com.example.testapp1.utils.Resource
import com.example.testapp1.utils.baseClasses.BaseFragment
import com.example.testapp1.utils.hasInternetConnection
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import javax.inject.Inject

class BreakingNewsFragment :
    BaseFragment<FragmentBreakingNewsBinding>(FragmentBreakingNewsBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: BreakingNewsViewModel by viewModels {
        viewModelFactory
    }
    private val newsAdapter by lazy { NewsAdapter() }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    override fun onAttach(context: Context) {
        DaggerFeatureComponent
            .builder()
            .build()
            .inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            navigate(it)
        }

        viewModel.breakingNews.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    handleSuccess(response)
                }
                is Resource.Error -> {
                    handleError(response)
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun handleSuccess(response: Resource<NewsResponse>) {
        hideProgressBar()
        response.data?.let { newsResponse ->
            newsAdapter.submitList(newsResponse.articles.toList())
            val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
            isLastPage = viewModel.breakingNewsPage == totalPages
            if (isLastPage) {
                rvBreakingNews.setPadding(0, 0, 0, 0)
            }
        }
    }

    private fun handleError(response: Resource<NewsResponse>) {
        hideProgressBar()
        response.message?.let { message ->
            Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingPageAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingPageAndNotLastPage && isAtLastItem && isNotAtBeginning
                    && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getBreakingNews("ru", requireContext().hasInternetConnection())
                isScrolling = false
            }
        }
    }

    private fun navigate(articleRemote: ArticleRemote) {
//        findNavController().navigate(
//            BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(
//                articleRemote,
//                null
//            )
//        )
        //TODO: deal with navigation
    }

    private fun setupRecyclerView() {
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }
}