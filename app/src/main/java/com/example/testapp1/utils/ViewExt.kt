package com.example.testapp1.utils

import android.view.View
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp1.R

fun View.visibilityIf(isVisible: Boolean) {
    visibility = if(isVisible) View.VISIBLE else View.GONE
}

//TODO: deal with scroll listener
//fun RecyclerView.requestScrollListener(isLastPage: Boolean, isLoading: Boolean) : RecyclerView.OnScrollListener {
//    val scrollListener = object : RecyclerView.OnScrollListener() {
//        var isScrolling = false
//
//        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//            super.onScrollStateChanged(recyclerView, newState)
//            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//                isScrolling = true
//            }
//        }
//
//        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            super.onScrolled(recyclerView, dx, dy)
//
//            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
//            val visibleItemCount = layoutManager.childCount
//            val totalItemCount = layoutManager.itemCount
//
//            val isNotLoadingPageAndNotLastPage = !isLoading && !isLastPage
//            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
//            val isNotAtBeginning = firstVisibleItemPosition >= 0
//            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
//            val shouldPaginate = isNotLoadingPageAndNotLastPage && isAtLastItem && isNotAtBeginning
//                    && isTotalMoreThanVisible && isScrolling
//            if (shouldPaginate) {
//                viewModel.getBreakingNews(
//                    getString(R.string.country_code),
//                    requireContext().hasInternetConnection()
//                )
//                isScrolling = false
//            }
//        }
//    }
//    return scrollListener
//}