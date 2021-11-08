package com.example.testapp1.di.domain.module

import com.example.testapp1.business.BreakingNewsInteractor
import com.example.testapp1.business.SaveRemoteArticleUseCase
import com.example.testapp1.business.SavedNewsInteractor
import com.example.testapp1.business.SearchedNewsInteractor
import com.example.testapp1.data.repository.NewsRepository
import com.example.testapp1.di.domain.DomainScope
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    @DomainScope
    fun provideBreakingNewsInteractor(repository: NewsRepository) : BreakingNewsInteractor {
        return BreakingNewsInteractor(repository)
    }

    @Provides
    @DomainScope
    fun provideSavedNewsInteractor(repository: NewsRepository) : SavedNewsInteractor {
        return SavedNewsInteractor(repository)
    }

    @Provides
    @DomainScope
    fun provideSaveRemoteArticleUseCase(repository: NewsRepository) : SaveRemoteArticleUseCase {
        return SaveRemoteArticleUseCase(repository)
    }

    @Provides
    @DomainScope
    fun provideSearchedNewsInteractor(repository: NewsRepository) : SearchedNewsInteractor {
        return SearchedNewsInteractor(repository)
    }
}