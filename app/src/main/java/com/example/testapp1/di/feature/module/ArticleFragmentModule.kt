package com.example.testapp1.di.feature.module

import androidx.lifecycle.ViewModelProvider
import com.example.testapp1.business.SaveRemoteArticleUseCase
import com.example.testapp1.di.feature.FeatureScope
import com.example.testapp1.feature.articleFragment.presentation.ArticleFragmentViewModel
import com.example.testapp1.feature.articleFragment.presentation.ArticleFragmentViewModelFactory
import com.example.testapp1.feature.articleFragment.ui.ArticleFragment
import dagger.Module
import dagger.Provides

@Module
class ArticleFragmentModule(private val articleFragment: ArticleFragment) {

    @Provides
    @FeatureScope
    fun provideArticleViewModelFactory(saveRemoteArticleUseCase: SaveRemoteArticleUseCase): ArticleFragmentViewModelFactory {
        return ArticleFragmentViewModelFactory(saveRemoteArticleUseCase)
    }

    @Provides
    @FeatureScope
    fun provideArticleFragmentViewModel(articleFragmentViewModelFactory: ArticleFragmentViewModelFactory)
    : ArticleFragmentViewModel {
        return ViewModelProvider(
            articleFragment,
            articleFragmentViewModelFactory
        ).get(ArticleFragmentViewModel::class.java)
    }
}