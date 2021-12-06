package com.example.testapp1.di.feature

import com.example.testapp1.feature.articleFragment.presentation.ArticleFragmentViewModel
import com.example.testapp1.feature.breakingNewsFragment.presentation.BreakingNewsViewModel
import com.example.testapp1.feature.savedNewsFragment.presentation.SavedNewsViewModel
import com.example.testapp1.feature.searchNewsFragment.presentation.SearchNewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {

    viewModel { ArticleFragmentViewModel(saveRemoteArticleUseCase = get()) }
    viewModel { BreakingNewsViewModel(breakingNewsUseCase = get()) }
    viewModel { SavedNewsViewModel(savedNewsInteractor = get()) }
    viewModel { SearchNewsViewModel(searchedNewsUseCase = get()) }
}