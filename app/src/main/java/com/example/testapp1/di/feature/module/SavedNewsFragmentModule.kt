package com.example.testapp1.di.feature.module

import androidx.lifecycle.ViewModelProvider
import com.example.testapp1.business.SavedNewsInteractor
import com.example.testapp1.di.feature.FeatureScope
import com.example.testapp1.feature.savedNewsFragment.presentation.SavedNewsViewModel
import com.example.testapp1.feature.savedNewsFragment.presentation.SavedNewsViewModelFactory
import com.example.testapp1.feature.savedNewsFragment.ui.SavedNewsFragment
import dagger.Module
import dagger.Provides

@Module
class SavedNewsFragmentModule(private val savedNewsFragment: SavedNewsFragment) {

    @Provides
    @FeatureScope
    fun provideSavedNewsViewModelFactory(savedNewsInteractor: SavedNewsInteractor): SavedNewsViewModelFactory {
        return SavedNewsViewModelFactory(savedNewsInteractor)
    }

    @Provides
    @FeatureScope
    fun provideSavedNewsViewModel(savedNewsViewModelFactory: SavedNewsViewModelFactory) : SavedNewsViewModel {
        return ViewModelProvider(
            savedNewsFragment,
            savedNewsViewModelFactory
        ).get(SavedNewsViewModel::class.java)
    }
}