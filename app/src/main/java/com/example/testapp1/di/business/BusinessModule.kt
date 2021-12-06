package com.example.testapp1.di.business

import com.example.testapp1.business.BreakingNewsUseCase
import com.example.testapp1.business.SaveRemoteArticleUseCase
import com.example.testapp1.business.SavedNewsInteractor
import com.example.testapp1.business.SearchedNewsUseCase
import com.example.testapp1.data.repository.NewsRepository
import org.koin.dsl.module

val businessModule = module {

    fun provideBreakingNewsUseCase(repository: NewsRepository): BreakingNewsUseCase {
        return BreakingNewsUseCase(repository)
    }

    fun provideSavedNewsInteractor(repository: NewsRepository): SavedNewsInteractor {
        return SavedNewsInteractor(repository)
    }

    fun provideSaveRemoteArticleUseCase(repository: NewsRepository): SaveRemoteArticleUseCase {
        return SaveRemoteArticleUseCase(repository)
    }

    fun provideSearchedNewsUseCase(repository: NewsRepository): SearchedNewsUseCase {
        return SearchedNewsUseCase(repository)
    }

    factory { provideBreakingNewsUseCase(repository = get()) }
    factory { provideSavedNewsInteractor(repository = get()) }
    factory { provideSaveRemoteArticleUseCase(repository = get()) }
    factory { provideSearchedNewsUseCase(repository = get()) }
}