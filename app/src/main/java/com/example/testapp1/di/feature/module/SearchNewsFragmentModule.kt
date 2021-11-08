package com.example.testapp1.di.feature.module

import androidx.lifecycle.ViewModelProvider
import com.example.testapp1.business.SearchedNewsInteractor
import com.example.testapp1.di.feature.FeatureScope
import com.example.testapp1.feature.searchNewsFragment.presentation.SearchNewsViewModel
import com.example.testapp1.feature.searchNewsFragment.presentation.SearchNewsViewModelFactory
import com.example.testapp1.feature.searchNewsFragment.ui.SearchNewsFragment
import dagger.Module
import dagger.Provides

@Module
class SearchNewsFragmentModule(private val searchNewsFragment: SearchNewsFragment) {

    @Provides
    @FeatureScope
    fun provideSearchNewsViewModelFactory(searchedNewsInteractor: SearchedNewsInteractor) : SearchNewsViewModelFactory {
        return SearchNewsViewModelFactory(searchedNewsInteractor)
    }

    @Provides
    @FeatureScope
    fun provideSearchNewsViewModel(searchNewsViewModelFactory: SearchNewsViewModelFactory) : SearchNewsViewModel {
        return ViewModelProvider(
            searchNewsFragment,
            searchNewsViewModelFactory
        ).get(SearchNewsViewModel::class.java)
    }
}