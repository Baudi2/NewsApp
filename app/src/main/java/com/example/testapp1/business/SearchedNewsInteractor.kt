package com.example.testapp1.business

import com.example.testapp1.data.remote.model.NewsResponse
import com.example.testapp1.data.repository.NewsRepository
import retrofit2.Response

class SearchedNewsInteractor(private val repository: NewsRepository) {

    suspend fun get(searchQuery: String, pageNumber: Int) : Response<NewsResponse> {
        return repository.getSearchNews(searchQuery, pageNumber)
    }
}